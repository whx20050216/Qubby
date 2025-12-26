package com.adplatform.qubby.controller;

import com.adplatform.qubby.model.Discussion;
import com.adplatform.qubby.model.User;
import com.adplatform.qubby.service.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/discussion")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    // 讨论列表
    @GetMapping
    public String list(Model model, HttpSession session) {
        // 检查登录
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("discussionList", discussionService.getAllDiscussions());
        return "discussionList";
    }

    // 讨论详情
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }

        Discussion discussion = discussionService.findById(id);
        if (discussion == null) {
            return "redirect:/discussion";
        }

        model.addAttribute("discussion", discussion);
        return "discussionDetail";
    }

    // 发布新讨论页面
    @GetMapping("/new")
    public String newDiscussionPage(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }
        return "newDiscussion";
    }

    // 发布新讨论提交
    @PostMapping("/new")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }

        User currentUser = (User) session.getAttribute("currentUser");
        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setContent(content);
        discussion.setAuthor(currentUser.getUsername());

        discussionService.create(discussion);
        return "redirect:/discussion";
    }

    // 返回讨论列表片段
    @GetMapping("/list-fragment")
    public String listFragment(Model model) {
        model.addAttribute("discussionList", discussionService.getAllDiscussions());
        return "discussionListFragment";  // 新建一个片段模板
    }

    //返回讨论详情片段
    @GetMapping("/{id}/replies-fragment")
    public String repliesFragment(@PathVariable Long id, Model model) {
        Discussion discussion = discussionService.findById(id);
        model.addAttribute("discussion", discussion);
        return "replyListFragment";
    }
}
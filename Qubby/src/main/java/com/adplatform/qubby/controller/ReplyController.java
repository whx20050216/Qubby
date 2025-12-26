package com.adplatform.qubby.controller;

import com.adplatform.qubby.model.Reply;
import com.adplatform.qubby.model.User;
import com.adplatform.qubby.service.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private DiscussionService discussionService;

    @PostMapping
    public String addReply(@RequestParam Long discussionId,
                           @RequestParam String content,
                           HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }

        User currentUser = (User) session.getAttribute("currentUser");
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setAuthor(currentUser.getUsername());

        discussionService.addReply(discussionId, reply);
        return "redirect:/discussion/" + discussionId;
    }
}
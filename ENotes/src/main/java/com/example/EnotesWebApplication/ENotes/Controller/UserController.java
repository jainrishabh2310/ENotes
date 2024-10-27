package com.example.EnotesWebApplication.ENotes.Controller;

import com.example.EnotesWebApplication.ENotes.Entity.Notes;
import com.example.EnotesWebApplication.ENotes.Entity.User;
import com.example.EnotesWebApplication.ENotes.Repository.HomeRepo;
import com.example.EnotesWebApplication.ENotes.Repository.NotesRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private HomeRepo homeRepo;

    @Autowired
    private NotesRepo notesRepo;

    @ModelAttribute
    public void addCommonData(Principal p, Model m) {
        String email = p.getName();
        User user = homeRepo.findByEmail(email);
        m.addAttribute("user", user);
    }

    @GetMapping("/home")
    public String home() {
        return "User/home";
    }

    @GetMapping("/add_notes")
    public String add() {
        return "User/add_notes";
    }

    @GetMapping("/view_page/{page}")
    public String view(@PathVariable int page, Model m, Principal p) {
        String email = p.getName();
        User user = homeRepo.findByEmail(email);

        // Correcting the page number to handle 0-based index for Spring Data Pageable
        int pageNumber = (page < 1) ? 0 : page - 1; // Adjust for zero-based index

        // Create the Pageable object with the correct page number
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());

        // Fetch paginated notes for the user
        Page<Notes> notesPage = notesRepo.findByNotesByUser(user.getId(), pageable);
        System.out.println(notesPage.getContent());

        // Add pagination attributes
        m.addAttribute("currentPage", page); // Pass original page number to UI
        m.addAttribute("totalPages", notesPage.getTotalPages());
        m.addAttribute("totalElements", notesPage.getTotalElements());
        m.addAttribute("notes", notesPage.getContent()); // Pass the content (list of notes)

        return "User/view_page";
    }

    // Add this method to handle the default view_page request
    @GetMapping("/view_page")
    public String viewFirstPage() {
        // Redirect to the first page
        return "redirect:/user/view_page/1";
    }


    @PostMapping("/savenotes")
    public String saveNote(@ModelAttribute Notes n, HttpSession session, Principal p) {
        System.out.println(n);

        String email = p.getName();
        User user = homeRepo.findByEmail(email);
        n.setUser(user);
        notesRepo.save(n);

        return "redirect:/user/add_notes";
    }

    @GetMapping("/edit_notes/{id}")
    public String edit(@PathVariable int id, Model m) {
        Optional<Notes> notes = notesRepo.findById(id);
        if (notes != null) {
            Notes n = notes.get();
            m.addAttribute("notes", n);

        }


        return "User/edit_notes";
    }

    @PostMapping("/updatenote")
    public String update(@ModelAttribute Notes notes, Principal p) {
        String email = p.getName();
        User user = homeRepo.findByEmail(email);
        notes.setUser(user);
        notesRepo.save(notes);

        if (notes != null) {
            return "redirect:/user/view_page/1";

        } else {
            return "redirect:/user/edit_notes";

        }
    }

    @GetMapping("/deletenotes/{id}")
    public String del(@PathVariable int id) {
        Optional<Notes> notes = notesRepo.findById(id);
        if (notes != null) {
            notesRepo.deleteById(id);
        }
        return "redirect:/user/view_page/1";

    }

    @GetMapping("/viewprofile")
    public String userupdate() {
        return "User/viewprofile";
    }

    @PostMapping("updateuser")
    public String update(@ModelAttribute User u) {
        Optional<User> user = homeRepo.findById(u.getId());

        if (user != null) {
            u.setRole(user.get().getRole());
            u.setPass(user.get().getPass());
            homeRepo.save(u);
        }


        return "redirect:/user/viewprofile";


    }
}

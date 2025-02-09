package net.vkolev.htmxjsonbexample.notes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.FragmentsRendering;


@Controller
public class NoteController {

    private final NotesRepository notesRepository;

    public NoteController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("notes", notesRepository.findAll());
        return "index";
    }

    @GetMapping("/{lang}")
    public String index(@PathVariable String lang, Model model) {
        model.addAttribute("notes", notesRepository.findAllWithTranslation(lang));
        model.addAttribute("lang", lang);
        return "index :: all-notes";

    }

    @GetMapping("/{lang}/search")
    public String search(@PathVariable String lang, String q, Model model) {
        model.addAttribute("notes", notesRepository.findAllWithTranslationAndQuery(lang, q));
        model.addAttribute("lang", lang);
        model.addAttribute("query", q);
        return "index :: all-notes";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public String create(@ModelAttribute NoteInput noteInput, Model model) {
        System.out.println(noteInput.getTitle());
        for (int i = 0; i < noteInput.getLang().size(); i++) {
            System.out.println(noteInput.getLang().get(i) + " : " + noteInput.getContent().get(i));
        }
        Note note = new Note();
        note.setTitle(noteInput.getTitle());
        note.setTranslations(generateTranslationString(noteInput));
        notesRepository.save(note);
        model.addAttribute("notes", notesRepository.findAll());
        return "index :: all-notes";
    }

    @GetMapping("/add-translation")
    public String addTranslation(Model model) {
        return "index :: translate-note";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id, Model model) {
        notesRepository.findById(Long.parseLong(id)).ifPresent(notesRepository::delete);
        return "index :: all-results";
    }

    private String generateTranslationString(NoteInput noteInput) {
        StringBuilder translations = new StringBuilder("{");
        for (int i = 0; i < noteInput.getLang().size(); i++) {
            translations.append("\"").append(noteInput.getLang().get(i)).append("\": \"");
            translations.append(noteInput.getContent().get(i)).append("\"");
            if (i != noteInput.getLang().size() - 1) {
                translations.append(",");
            }
        }
        translations.append("}");
        return translations.toString();
    }
}

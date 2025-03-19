package com.maps.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.maps.service.ServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/storage")
public class ControllerUpload {

    private final ServiceStorage serviceStorage;

    @Autowired
    public ControllerUpload(ServiceStorage serviceStorage) {
        this.serviceStorage = serviceStorage;
    }

    @GetMapping("")
    public ModelAndView listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", serviceStorage.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(ControllerUpload.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("title", "Upload");
        return modelAndView;
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = serviceStorage.loadAsResource(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @PostMapping("")
    public String handleFileUpload(@RequestParam("file") MultipartFile[] files, RedirectAttributes redirectAttributes) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                serviceStorage.store(file);
            }
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + files.length + " files(s)!");
        return "redirect:/";
    }
}
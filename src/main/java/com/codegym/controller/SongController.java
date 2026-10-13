package com.codegym.controller;

import com.codegym.model.Song;
import com.codegym.model.SongForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    // Đường dẫn thư mục lưu file upload trên máy tính của bạn
    private final String uploadPath = "C:/Users/ASUS/Desktop/test-music-saver/";

    // Danh sách lưu trữ bài hát tạm thời
    private static final List<Song> songList = new ArrayList<>();

    // 1. Hiển thị danh sách bài hát
    @GetMapping
    public String listSongs(Model model) {
        model.addAttribute("songs", songList);
        return "list";
    }

    // 2. Hiển thị Form upload
    @GetMapping("/upload")
    public ModelAndView showUploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("songForm", new SongForm());
        return modelAndView;
    }

    // 3. Xử lý lưu bài hát
    @PostMapping("/save")
    public String saveSong(@ModelAttribute("songForm") SongForm songForm, Model model) {
        MultipartFile multipartFile = songForm.getSongFile();
        String fileName = multipartFile.getOriginalFilename();

        // Validate đuôi file
        if (fileName == null || !isValidAudioFile(fileName)) {
            model.addAttribute("errorMessage", "File không hợp lệ! Chỉ chấp nhận đuôi .mp3, .wav, .ogg, .m4p");
            return "upload";
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Copy file tải lên vào thư mục lưu trữ
            FileCopyUtils.copy(songForm.getSongFile().getBytes(), new File(uploadPath + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Tạo đối tượng Song để lưu vào danh sách
        Song song = new Song(songForm.getName(), songForm.getArtist(), songForm.getGenre(), fileName);
        songList.add(song);

        // Redirect đến trang danh sách
        return "redirect:/songs";
    }

    // Hàm hỗ trợ kiểm tra đuôi file
    private boolean isValidAudioFile(String fileName) {
        String lowerName = fileName.toLowerCase();
        return lowerName.endsWith(".mp3") || lowerName.endsWith(".wav")
                || lowerName.endsWith(".ogg") || lowerName.endsWith(".m4p");
    }
}
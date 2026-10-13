package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private String name;
    private String artist;
    private String genre;
    private MultipartFile songFile; // Hứng file tải lên từ <input type="file">

    public SongForm() {}

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public MultipartFile getSongFile() { return songFile; }
    public void setSongFile(MultipartFile songFile) { this.songFile = songFile; }
}
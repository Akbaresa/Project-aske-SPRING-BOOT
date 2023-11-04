package esa.askerestful.controller;

import esa.askerestful.entity.Gambar;
import esa.askerestful.entity.User;
import esa.askerestful.model.DownloadGambarResponse;
import esa.askerestful.model.FileResponse;
import esa.askerestful.model.GambarResponse;
import esa.askerestful.model.WebResponse;
import esa.askerestful.service.GambarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;

@RestController
public class GambarController {

    @Autowired
    private GambarService gambarService;

    @PostMapping(
            path = "api/upload-gambar"
    )
    public FileResponse<GambarResponse> uploadGambar(User user ,@RequestParam("gambar") MultipartFile file) {
        try {
            GambarResponse gambarResponse = gambarService.uploadGambar(user ,file);
            return new FileResponse<>(gambarResponse,HttpStatus.CREATED  );
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi kesalahan dalam mengunggah gambar", e);
        }
    }

    @GetMapping(
            path = "/api/gambar/{namaGambar}"
    )
    public ResponseEntity<?> getGambar(@PathVariable("namaGambar") String namaGambar) throws Exception{
        byte[] gambarData = gambarService.getGambar(namaGambar);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(gambarData);
    }






}

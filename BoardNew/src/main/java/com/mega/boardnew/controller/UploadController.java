package com.mega.boardnew.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mega.boardnew.bean.AttachFileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Slf4j
@RequestMapping("/upload")
public class UploadController {

	@GetMapping("uploadForm")
	public void uploadForm() {
		log.info("[UploadController] uploadForm() called");
	}

	@PostMapping("uploadFormAction")
	public String upload(MultipartFile[] uploadFile, Model model) {
		String uploadFolder = "/Users/ryankim/upload/temp";

		for (MultipartFile f : uploadFile) {
			log.info("filename: " + f.getOriginalFilename());
			log.info("file size : " + f.getSize());

			// File Creation(Empty)
			File saveFile = new File(uploadFolder, f.getOriginalFilename());

			// Contents Copy
			try {
				f.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "/upload/result";
	}

	@GetMapping("uploadAjax")
	public void uploadAjax() {
		log.info("[UploadController] uploadAjax() called");
	}

	@PostMapping(value = "uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody // 메서드 종료시 html로 가지 않고 데이터로 리턴 RestController
	public List uploadAjaxPost(MultipartFile[] uploadFile, Model model) {
		log.info("[uploadController] uploadAjaxAction() called ok");
		List<AttachFileVO> fileList = new ArrayList();

		String uploadFolder = "/Users/ryankim/upload/temp";
		String uploadFolderPath = getFolder();

		// yyyy/MM/dd 경로 만들기.
		File uploadPath = new File(uploadFolder, getFolder());

		if (uploadPath.exists()) { // 디렉토리가 존재하는지.
			log.info("[directory already exists");

		} else {
			uploadPath.mkdirs();
		}

		for (MultipartFile f : uploadFile) {
			log.info("filename: " + f.getOriginalFilename());
			log.info("file size : " + f.getSize());

			AttachFileVO attachFileVO = new AttachFileVO();

			// UUID 적용.
			// Network 상에서 각각의 객체를 식별하기 위해 사용.
			String uploadFileName = f.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			attachFileVO.setFilename(uploadFileName);
			attachFileVO.setUuid(uuid.toString());
			attachFileVO.setUploadPath(uploadFolderPath);

			// File Creation(Empty)
			// File saveFile = new File(uploadFolder, f.getOriginalFilename());

			File saveFile = new File(uploadPath, uploadFileName);

			// Contents Copy
			try {
				f.transferTo(saveFile);

				if (checkImageType(saveFile)) {
					log.info("Image File");

					// 썸네일 파일 생성 경로 설정
//			        File thumbnailFile = new File(uploadPath, "s_" + saveFile.getName());
//
//			        // 썸네일 생성
//			        Thumbnails.of(saveFile)
//			                  .size(200, 200) // 썸네일 크기 지정
//			                  .toFile(thumbnailFile);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					InputStream in = new FileInputStream(saveFile);
					Thumbnailator.createThumbnail(in, thumbnail, 100, 100);
					thumbnail.close();
					in.close();

					fileList.add(attachFileVO);

				}

				else {
					log.info("Not Image");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str;
	}

	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}

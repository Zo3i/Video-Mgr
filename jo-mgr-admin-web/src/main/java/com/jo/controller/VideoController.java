package com.jo.controller;

import com.jo.pojo.Bgm;
import com.jo.service.VideoService;
import com.jo.utils.JSONResult;
import com.jo.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/video")
public class VideoController {

	@Autowired
	private VideoService videoService;
    @GetMapping("/showAddBgm")
    public String login() {
        return "video/addBgm";
    }
    @PostMapping("/bgmUpload")
    @ResponseBody
    public JSONResult bgmUpload (@RequestParam("file") MultipartFile[] file) throws Exception {

		//文件上传路径
		String location = "E:"+ File.separator + "WeixinApp" + File.separator
                              + "userFile";
		//数据库保存路径
		String uploadPathDB =  File.separator + "bgm";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (file != null && file.length > 0) {
				String fileName = file[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					//文件的最终保存路径
					String finalPath = location + uploadPathDB + File.separator + fileName;
					//设置数据库保存路径
					uploadPathDB += (File.separator + fileName);
					File outFile = new File(finalPath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						//创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			} else {
				return JSONResult.errorMsg("上传出错!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			} else {
				return JSONResult.errorMsg("上传出错!");
			}
		}
		return JSONResult.ok(uploadPathDB);
	}
	@PostMapping("/addBgm")
	@ResponseBody
	public JSONResult addBgm(Bgm bgm) {
    	System.out.println("上傳音樂");
		videoService.addBgm(bgm);
    	return JSONResult.ok();
	}
	@GetMapping("/showBgmList")
	public String showBgmList() {
    	return "/video/bgmList";
	}
	@PostMapping("/queryBgmList")
	@ResponseBody
	public PagedResult queryBgmList(Integer page) {
    	return videoService.queryBgmList(page, 10);
	}
	@PostMapping("/delBgm")
	@ResponseBody
	public JSONResult delBgm(String bgmId) {
		videoService.deleteBgm(bgmId);
		return JSONResult.ok();
	}
}

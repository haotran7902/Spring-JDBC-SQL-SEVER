package com.csdlpt.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csdlpt.demo.DAO.DAO;
import com.csdlpt.demo.Entity.KhachHang;

@Controller
public class KhachHangController {

	private DAO dao = new DAO();
	
	@GetMapping("/khachhangs")
	public String getAll(@RequestParam(name = "page", required = false) String page, Model model) {
		int begin = 0;
		if(page != null) begin = Integer.parseInt(page) * 20;
		model.addAttribute("khachhangs", dao.getAll(begin));
		model.addAttribute("total", dao.getTotalRow());
		model.addAttribute("currentPage", begin/20);
		int endPage = dao.getTotalRow() / 20;
		if(dao.getTotalRow() % 20 == 0) endPage --;
		model.addAttribute("endPage", endPage);
		return "khachhangs";
	}
	
	@GetMapping("/khachhang/{id}")
	public String editForm(Model model, @PathVariable String id) {
		model.addAttribute("khachhang", dao.getById(id));
		return "edit-khachhang";
	}
	
	@GetMapping("/khachhang/new")
	public String newForm(Model model) {
		model.addAttribute("khachhang", new KhachHang());
		return "new-khachhang";
	}
	
	@PostMapping("/khachhang/save/{id}")
	public String add(KhachHang khachHang, Model model) {
		dao.add(khachHang, model);
		String message = (String) model.getAttribute("message");
		if(message != null) {
			System.out.println(model.getAttribute("message"));
			return "redirect:/khachhang/new";
		}
		System.out.println("CẬP NHẬT THÀNH CÔNG");
		return "redirect:/khachhangs";
	}
	
	@PutMapping("/khachhang/save/{id}")
	public String update(KhachHang khachHang, Model model) {
		dao.update(khachHang, model);
		String message = (String) model.getAttribute("message");
		if(message != null) {
			System.out.println(model.getAttribute("message"));
			return "redirect:/khachhang/{id}";
		}
		System.out.println("CẬP NHẬT THÀNH CÔNG");
		return "redirect:/khachhangs";
	}
	
	@DeleteMapping("/khachhang/{id}")
	public String delete(@PathVariable String id) {
		dao.delete(id);
		return "redirect:/khachhangs";
	}
}

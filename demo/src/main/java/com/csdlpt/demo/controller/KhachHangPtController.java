package com.csdlpt.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csdlpt.demo.DAO.DAOPT;
import com.csdlpt.demo.Entity.KhachHang;

@Controller
public class KhachHangPtController {
	
	private DAOPT dao = new DAOPT();
	
	@GetMapping("/khachhangs2")
	public String getAll(@RequestParam(name = "page", required = false) String page, 
			@RequestParam(name = "ChiNhanh", required = false) String ChiNhanh, Model model) {
		int begin = 0;
		if(page != null) begin = Integer.parseInt(page) * 20;
		if(ChiNhanh == null) ChiNhanh = "NT";
		if(ChiNhanh.equalsIgnoreCase("HN")) {
			List<KhachHang> list = dao.getHN(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowHN());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);
			
			int endPage = dao.getTotalRowHN() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
		} else if(ChiNhanh.equalsIgnoreCase("QN")) {
			List<KhachHang> list = dao.getQN(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowQN());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);

			int endPage = dao.getTotalRowQN() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
			
		} else if(ChiNhanh.equalsIgnoreCase("NT")) {
			List<KhachHang> list = dao.getNT(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowNT());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);

			int endPage = dao.getTotalRowNT() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
		} else if(ChiNhanh.equalsIgnoreCase("TB")) {
			List<KhachHang> list = dao.getTB(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowTB());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);

			int endPage = dao.getTotalRowTB() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
		} else if(ChiNhanh.equalsIgnoreCase("DN")) {
			List<KhachHang> list = dao.getDN(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowDN());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);

			int endPage = dao.getTotalRowDN() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
		} else if(ChiNhanh.equalsIgnoreCase("HUE")) {
			List<KhachHang> list = dao.getHUE(begin);
			model.addAttribute("khachhangs", list);
			model.addAttribute("total", dao.getTotalRowHUE());
			model.addAttribute("currentPage", begin/20);
			model.addAttribute("CurrentCN", ChiNhanh);

			int endPage = dao.getTotalRowHUE() / 20;
			if(list.size()%20 == 0) endPage--;
			model.addAttribute("endPage", endPage);
			return "khachhangs2";
		}
		return "khachhangs2";
	}

}

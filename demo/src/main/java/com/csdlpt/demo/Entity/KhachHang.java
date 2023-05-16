package com.csdlpt.demo.Entity;

import jakarta.persistence.Entity;

@Entity
public class KhachHang {
	private String MaKH;
	private String TenKH;
	private String DiaChi;
	private String SDT;
	
	public KhachHang() {}
	
	public KhachHang(String maKH, String tenKH, String diaChi, String sDT) {
		MaKH = maKH;
		TenKH = tenKH;
		DiaChi = diaChi;
		SDT = sDT;
	}
	
	public KhachHang(String maKH, String tenKH) {
		MaKH = maKH;
		TenKH = tenKH;
	}

	public String getMaKH() {
		return MaKH;
	}
	
	public void setMaKH(String maKH) {
		MaKH = maKH;
	}
	
	public String getTenKH() {
		return TenKH;
	}
	
	public void setTenKH(String tenKH) {
		TenKH = tenKH;
	}
	
	public String getDiaChi() {
		return DiaChi;
	}
	
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
	
	public String getSDT() {
		return SDT;
	}
	
	public void setSDT(String sDT) {
		SDT = sDT;
	}
	
	@Override
	public String toString() {
		return "KhachHang [MaKH=" + MaKH + ", TenKH=" + TenKH + ", DiaChi=" + DiaChi + ", SDT=" + SDT + "]";
	}
}

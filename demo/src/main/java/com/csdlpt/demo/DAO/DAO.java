package com.csdlpt.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.csdlpt.demo.Entity.KhachHang;

public class DAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private static final String SELECT_ALL = "select * from KhachHang\r\n"
			+ "order by MaKH\r\n"
			+ "OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_BY_ID = "select * from KhachHang where MaKH = ?";
	private static final String INSERT = "insert into KhachHang values(?, ?, ?, ?, default)";
	private static final String UPDATE = "update KhachHang set TenKH = ?, DiaChi = ?, SDT = ? where MaKH = ?";
	private static final String DELETE = "delete from KhachHang where MaKH = ?";

	protected Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://DESKTOP-SI8SM5O\\NHOM5CSDLPT:1433;databaseName=QLKH1;encrypt=true;trustServerCertificate=true;";
			conn = DriverManager.getConnection(url, "sa", "123");
		} catch (Exception e) {
			System.out.println("ket noi that bai!");
			System.err.println(e.getMessage() + "\n" + e.getClass() + "\n" + e.getCause());
		}
		return conn;
	}

	public List<KhachHang> getAll(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public KhachHang getById(String id) {
		KhachHang khachHang = new KhachHang();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_BY_ID);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				khachHang = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (Exception e) {
		}
		return khachHang;
	}
	
	public void add(KhachHang khachHang, Model model) {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(INSERT);
			ps.setString(1, khachHang.getMaKH());
			ps.setString(2, khachHang.getTenKH());
			ps.setString(3, khachHang.getDiaChi());
			ps.setString(4, khachHang.getSDT());
			
			int result = ps.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			model.addAttribute("message", e.getMessage());
		}
	}
	
	public void update(KhachHang khachHang, Model model) {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(UPDATE);
			ps.setString(1, khachHang.getTenKH());
			ps.setString(2, khachHang.getDiaChi());
			ps.setString(3, khachHang.getSDT());
			ps.setString(4, khachHang.getMaKH());
			int result = ps.executeUpdate();
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
	}
	
	public void delete(String id) {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(DELETE);
			ps.setString(1, id);
			int result = ps.executeUpdate();
		} catch (Exception e) {
		}
	}
	
	public int getTotalRow() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement("select count(*) from KhachHang");
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}

	public static void main(String[] args) {
		DAO dao = new DAO();
		KhachHang khachHang = new KhachHang("AH00001", "Quang", "Ha Noi", "0123456789");
		KhachHang khachHang1 = new KhachHang("KH00001", "tri", "Thái Bình", "086888888");
		
//		System.out.println("So luong khach hang truoc khi them: ");
//		System.out.println(dao.getTotalRow());
//		dao.update(khachHang1);
//		dao.add(khachHang);
		
//		System.out.println("So luong khach hang sau khi them: ");
//		System.out.println(dao.getTotalRow());
	}
}

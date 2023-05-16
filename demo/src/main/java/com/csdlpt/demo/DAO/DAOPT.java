package com.csdlpt.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.csdlpt.demo.Entity.KhachHang;

public class DAOPT {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private static final String SELECT_ALL_MB = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from QN.QLCH_QN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from QN.QLCH_QN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "-- HA NOI --\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HN.QLCH_HN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HN.QLCH_HN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "-- THAI BINH --\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from CHTB.QLCH_TB.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from CHTB.QLCH_TB.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY";
	private static final String SELECT_ALL_MN = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from NT.QLCH_NT.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from NT.QLCH_NT.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "-- DA NANG --\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from DN.QLCH_DN.dbo.GiaoDich where SoLuong > 2) GiaoDich\r\n"
			+ "ON KhachHang.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			+ "JOIN (select MaNV from DN.QLCH_DN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham \r\n"
			+ "ON SanPham.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			+ "-- HUE --\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HUE.QLCH_HUE.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HUE.QLCH_HUE.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY";
	
	private static final String SELECT_ALL_QN = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from QN.QLCH_QN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from QN.QLCH_QN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_ALL_HN = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HN.QLCH_HN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HN.QLCH_HN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_ALL_TB = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from CHTB.QLCH_TB.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from CHTB.QLCH_TB.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_ALL_DN = "SELECT KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from DN.QLCH_DN.dbo.GiaoDich where SoLuong > 2) GiaoDich\r\n"
			+ "ON KhachHang.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			+ "JOIN (select MaNV from DN.QLCH_DN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham \r\n"
			+ "ON SanPham.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_ALL_HUE = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HUE.QLCH_HUE.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HUE.QLCH_HUE.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String SELECT_ALL_NT = "SELECT  KhachHang.MaKH, KhachHang.TenKH\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from NT.QLCH_NT.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from NT.QLCH_NT.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";

	private static final String COUNT_QN = "SELECT  count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from QN.QLCH_QN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from QN.QLCH_QN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			;
	private static final String COUNT_HN = "SELECT  count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HN.QLCH_HN.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HN.QLCH_HN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			+ "order by MaKH OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
	private static final String COUNT_TB = "SELECT  count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from CHTB.QLCH_TB.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from CHTB.QLCH_TB.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			;
	private static final String COUNT_DN = "SELECT count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from DN.QLCH_DN.dbo.GiaoDich where SoLuong > 2) GiaoDich\r\n"
			+ "ON KhachHang.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaKH COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			+ "JOIN (select MaNV from DN.QLCH_DN.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham \r\n"
			+ "ON SanPham.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS = GiaoDich.MaSP COLLATE SQL_Latin1_General_CP1_CI_AS\r\n"
			;
	private static final String COUNT_HUE = "SELECT  count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from HUE.QLCH_HUE.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from HUE.QLCH_HUE.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			;
	private static final String COUNT_NT = "SELECT  count(*)\r\n"
			+ "FROM (SELECT MaKH, TenKH FROM KhachHang) KhachHang\r\n"
			+ "JOIN (select MaNV, MaKH, MaSP from NT.QLCH_NT.dbo.GiaoDich where SoLuong > 2) GiaoDich \r\n"
			+ "	ON KhachHang.MaKH = GiaoDich.MaKH\r\n"
			+ "JOIN (select MaNV from NT.QLCH_NT.dbo.NhanVien) NhanVien ON GiaoDich.MaNV = NhanVien.MaNV\r\n"
			+ "JOIN (select MaSP from SanPham where Gia > 200000) SanPham ON SanPham.MaSP = GiaoDich.MaSP\r\n"
			;
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
	
	public List<KhachHang> getQN(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_QN);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}

	public List<KhachHang> getHN(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_HN);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public List<KhachHang> getTB(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_TB);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public List<KhachHang> getDN(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_DN);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public List<KhachHang> getHUE(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_HUE);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public List<KhachHang> getNT(Integer number) {
		List<KhachHang> list = new ArrayList<>();
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(SELECT_ALL_NT);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KhachHang(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public int getTotalRowQN() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_QN);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}
	
	public int getTotalRowHN() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_HN);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}
	
	public int getTotalRowTB() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_TB);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}
	
	public int getTotalRowDN() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_DN);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}
	
	public int getTotalRowHUE() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_HUE);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}
	
	public int getTotalRowNT() {
		try {
			conn = getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(COUNT_NT);
			rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {}
		return 0;
	}

	public static void main(String[] args) {
		DAOPT dao = new DAOPT();
//		List<KhachHang> list = dao.getDN(20);
//		System.out.println(list.size());
		System.out.println(dao.getTotalRowDN());
	}
}

package itView.springboot.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.mapper.InhoMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.Point;
import itView.springboot.vo.Product;
import itView.springboot.vo.Report;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InhoService {
	
	private final InhoMapper mapper;

	public int insertUser(User u) {
		return mapper.insertUser(u);
	}

	public User login(User u) {
		return mapper.login(u);
	}

	public int checkId(String userId) {
		return mapper.checkId(userId);
	}

	public void enrollNotice(Board b, String uploadedFiles, HttpSession session) {
		 User user = (User)session.getAttribute("loginUser");
	        b.setUserNo(user.getUserNo());

	        // 게시글 저장 → boardId 생성
	        mapper.enrollNotice(b);
	        int boardId = b.getBoardId();

	        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
	            String[] files = uploadedFiles.split(",");
	            String tempDir = "c:/uploadFilesFinal/temp";
	            String finalDir = "c:/uploadFilesFinal/notice";

	            File noticeFolder = new File(finalDir);
	            if (!noticeFolder.exists()) {
	                noticeFolder.mkdirs(); // ❗ 폴더 생성
	            }

	            for(String fileName : files){
	                if(fileName == null || fileName.trim().isEmpty()) continue;

	                File tempFile = new File(tempDir, fileName);
	                File finalFile = new File(finalDir, fileName);

	                // Windows에서 renameTo 실패할 경우 대비
	                if (!tempFile.renameTo(finalFile)) {
	                    java.nio.file.Path source = tempFile.toPath();
	                    java.nio.file.Path target = finalFile.toPath();
	                    try {
	                        java.nio.file.Files.copy(source, target);
	                        tempFile.delete();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }

	                // DB 저장
	                Attachment attm = new Attachment();
	                attm.setAttmName(fileName.substring(fileName.indexOf("_")+1));
	                attm.setAttmRename(fileName);
	                attm.setAttmPath("/uploadFilesFinal/notice");
	                attm.setPositionNo(boardId);
	                mapper.insertAttachment(attm);
	            }
	        }
	}

	
	public int enrollCoupon(Coupon c) {
		return mapper.enrollCoupon(c);
	}

	public List<User> selectTargetUser(String couponTarget) {
		return mapper.selectTargetUser(couponTarget);
	}

	public int insertCouponBox(Map<String, Object> map) {
		return mapper.insertCouponBox(map);
	}

	public int getCouponCount(HashMap<String, String> map) {
		return mapper.getCouponCount(map);
	}

	public ArrayList<Coupon> selectCouponList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectCouponList(map, rowBounds);
	}

	public int getCouponBoardCount(HashMap<String, String> map) {
		return mapper.getCouponBoardCount(map);
	}

	public ArrayList<Board> selectCouponBoardList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectCouponBoardList(map, rowBounds);
	}

	public Coupon selectCoupon(int cId) {
		Coupon c = mapper.selectCoupon(cId);
		return c;
	}

	public Board selectCouponBoard(int bId) {
		Board b = mapper.selectCouponBoard(bId);
		return b;
	}

	public int updateCoupon(Coupon c) {
		return mapper.updateCoupon(c);
	}

	public int updateCouponBoard(Board b) {
		return mapper.updateCouponBoard(b);
	}

	public void updateAttachment(int boardId, String[] uploadedFiles) {
		if (uploadedFiles != null && uploadedFiles.length > 0) {
            String tempDir = "c:/uploadFilesFinal/temp";
            String finalDir = "c:/uploadFilesFinal/notice";

            File noticeFolder = new File(finalDir);
            if (!noticeFolder.exists()) {
                noticeFolder.mkdirs();
            }

            for(String fileName : uploadedFiles){
                if(fileName == null || fileName.trim().isEmpty()) continue;

                File tempFile = new File(tempDir, fileName);
                File finalFile = new File(finalDir, fileName);

                // 이동 실패 시 copy + delete
                if(!tempFile.renameTo(finalFile)){
                    try {
                        java.nio.file.Files.copy(tempFile.toPath(), finalFile.toPath());
                        tempFile.delete();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                // DB 저장
                Attachment attm = new Attachment();
                attm.setAttmName(fileName.substring(fileName.indexOf("_")+1));
                attm.setAttmRename(fileName);
                attm.setAttmPath("/uploadFilesFinal/notice");
                attm.setPositionNo(boardId);
                mapper.insertAttachment(attm);
            }
        }
	}

	public int enrollPoint(Point p) {
		return mapper.enrollPoint(p);
	}

	public int updatePoint(Point p) {
		return mapper.updatePoint(p);
	}
	
	public int getPointCount(HashMap<String, String> map) {
		return mapper.getPointCount(map);
	}

	public ArrayList<Point> selectPointList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectPointList(map, rowBounds);
		
	}

	public int getPointByName(String pointName) {
		return mapper.getPointByName(pointName);
	}

	public int addPoint(Map<String, Object> map) {
		return mapper.addPoint(map);
	}

	public int getRankingCount(HashMap<String, String> map) {
		return mapper.getRankingCount(map);
	}

	public ArrayList<Product> selectRankingList(HashMap<String, String> map) {
		ArrayList<Product> list = mapper.selectRankingList(map);

        // 썸네일 세팅
        for (Product p : list) {
            String thumbnail = mapper.selectThumbnail(p.getProductNo());
            p.setFirstImage(thumbnail);
        }

        return list;
	}

	public Point selectPoint(int pNo) {
		Point p = mapper.selectPoint(pNo);
		return p;
	}

	public int getNoticeCount(HashMap<String, String> map) {
		return mapper.getNoticeCount(map);
	}

	public ArrayList<Board> selectNoticeList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectNoticeList(map, rowBounds);
	}

	public int deleteNotice(int bId) {
		return mapper.deleteNotice(bId);
	}

	public int deleteCoupon(int cNo) {
		return mapper.deleteCoupon(cNo);
	}

	public int deletePoint(int pNo) {
		return mapper.deletePoint(pNo);
	}

	public int updateNotice(Board b) {
		return mapper.updateNotice(b);
	}

	public Board selectNotice(int bId) {
		Board b = mapper.selectNotice(bId);
		return b;
	}

	public Product selectProduct(int pNo) {
		Product p = mapper.selectProduct(pNo);
		return p;
		
	}

	public int getReportProductCount(HashMap<String, String> map) {
		return mapper.getReportProductCount(map);
	}

	public ArrayList<Product> selectReportProductList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportProductList(map, rowBounds);
	}

	public Product selectReportProduct(int pNo) {
		Product p = mapper.selectReportProduct(pNo);
		String thumbnail = mapper.selectThumbnail(p.getProductNo());
        p.setFirstImage(thumbnail);
		return p;
	}

//	public Report selectReport(int pNo) {
//		Report r = mapper.selectReport(pNo);
//		return r;
//	}

	public int getReportCount(int pNo) {
		return mapper.getReportCount(pNo);
	}

	public ArrayList<Report> selectReportList(PageInfo pi, int pNo) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportList(pNo, rowBounds);
	}

	public int updateReportModifyDate(Map<String, Object> map) {
		return mapper.updateReportModifyDate(map);
	}

	public int updateProductState(int productNo) {
		return mapper.updateProductState(productNo);
	}
	
	public void activateExpiredProducts(LocalDate today) {
		mapper.activateProducts(today);
	}

	public int enrollReport(Report r) {
		return mapper.enrollReport(r);
	}
	
	

}

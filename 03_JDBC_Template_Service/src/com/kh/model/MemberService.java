package com.kh.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import static com.kh.common.JDBCTemplate.*;

/*
 * Service : 기존의 DAO의 역할을 분담
 * 			 컨트롤러에서 서비스 호출후 서비스를 거쳐서 DAO로 넘어갈 것
 * 			 DAO호출시 커넥션 객체와 기존에 넘기고자 했던 매개변수를 같이 넘겨줌
 * 			 DAO가 작업이 끝나면 서비스에게 결과를 돌려주고 그에따른 트랜잭션 처리를 같이 해줌
 * 			 => 서비스단을 추가함으로써 DAO에는 순수하게 SQL문을 처리하는 부분만 남게됨
 * 
*/
public class MemberService {
	
	
	public int insertMember(Member m) {
		
		Connection conn = getConnection();
		
		// Dao 호출시 Connection 객체와 기존에 넘기고자 했던 매개변수를 함께 전달
		int result = new MemberDao().insertMember(conn,m);
		
		// 6. 트랜잭션처리. !!!! 반환값이 int일 경우 트랜잭션 반드시 작성!!!!
		if(result > 0 ) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 7. Connection 객체 반남
		JDBCTemplate.close(conn);
		
		//8. 결과 값을 컨트롤러에게 반환
		return result;
		
	}
	
	public ArrayList<Member> selectAll(){
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		
		//2. dao 호출해서 리턴값 받기.
		ArrayList<Member> list = new MemberDao().selectAll(conn);
		
		// 3 닫기
		close(conn);
		
		//4 리턴
		return list;
		
	}
	public Member selectByUserId(String userId) {
		
		// 1. Connection 객체 생성
			Connection conn = getConnection();
		
		// 2. DAO 호출시 conn 객체와 , 전달받은 값을 함께 넘겨주기
			Member m = new MemberDao().selectByUserId(userId,conn);
		
		// 3. conn 반납
			close(conn);
		// 4. 컨트롤러에게 전달받은 값 리턴
			return m;
		
	}
	public ArrayList<Member> selectByUserName(String keyword){
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		
		// 2. DAO 호출시 conn 객체와, 전달받은 값을 함께 넘겨주기
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword,conn);
		
		// 3. conn 반납
		close(conn);
		
		// 4. 컨트롤러에게 전달받은 값 리턴
		return list;
	}
	
	public int updateMember(Member m) {
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		// 2. DAO 호출시 conn 객체와, 전달받은 값을 함께 넘겨주기
		int result = new MemberDao().updateMember(m, conn);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		// 3. conn 반납
		close(conn);
		// 4. 컨트롤러에게 전달받은 값 리턴
		return result;
	
	}
	
	public int deleteMember(String userId, String userPwd) {
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		// 2. DAO 호출시 conn 객체와, 전달받은 값을 함께 넘겨주기
		int result = new MemberDao().deleteMember(userId, userPwd, conn);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		// 3. conn 반납
		close(conn);
		// 4. 컨트롤러에게 전달받은 값 리턴
		return result;
	}
}

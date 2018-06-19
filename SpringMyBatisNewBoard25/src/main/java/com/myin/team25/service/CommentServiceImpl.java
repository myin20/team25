package com.myin.team25.service;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myin.team25.domain.CommentVo;
import com.myin.team25.persistence.CommentService_Mapper;

@Service("commentServiceImpl")
public class CommentServiceImpl implements CommentService {

	@Autowired	
	SqlSession sqlSession;
	
	@Override
	public ArrayList<CommentVo> SelectCommentAll(int bbidx) {
	
//		System.out.println("comment bbidx:"+bbidx);
		ArrayList<CommentVo> alist = null;
		CommentService_Mapper csm = sqlSession.getMapper(CommentService_Mapper.class);
//		System.out.println("csm:"+csm);
		alist = csm.SelectCommentAll(bbidx);
//		System.out.println("a:"+alist);
		return alist;
	}
	
	@Override
	public ArrayList<CommentVo> getCommentMore(int bbidx, int block,int perBlockNum) {
			
		ArrayList<CommentVo> alist = null;
		CommentService_Mapper csm = sqlSession.getMapper(CommentService_Mapper.class);
		alist = csm.getCommentMore(bbidx,block,perBlockNum);
		
		return alist;
	}
	

	@Override
	public int insertComment(CommentVo cv) {		
				
		CommentService_Mapper csm = sqlSession.getMapper(CommentService_Mapper.class);
		int result = csm.insertComment(cv);	

		return result;
	}

	@Override
	public int deleteComment(CommentVo cv) {
		
		CommentService_Mapper csm = sqlSession.getMapper(CommentService_Mapper.class);
		int result = csm.deleteComment(cv);		
		
		return result;
	}

}

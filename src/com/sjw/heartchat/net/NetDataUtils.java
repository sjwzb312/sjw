package com.sjw.heartchat.net;

import android.content.Context;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.bean.CommentBean;
import com.sjw.heartchat.bean.MsgBean;

/**
 * 获得网络数据请求
 * 
 * @author gg
 * 
 */
public class NetDataUtils {
	/**
	 * 获得单个消息的内容
	 * 
	 * @param context
	 * @param msgId
	 * @param getListener
	 */
	public static void queryOneMsg(Context context, String msgId,
			GetListener<MsgBean> getListener) {
		BmobQuery<MsgBean> bmobQuery = new BmobQuery<MsgBean>();
		bmobQuery.getObject(context, msgId, getListener);
	}

	/**
	 * 发表评论
	 * 
	 * @param context
	 * @param commentBean
	 */
	public static void sendComment(Context context, CommentBean commentBean,
			SaveListener saveListener) {
		if (commentBean == null) {
			return;
		}
		commentBean.save(context, saveListener);
	}

	/**
	 * 获取评论
	 * 
	 * @param context
	 * @param msgId
	 * @param findListener
	 */
	public static void getAllCommentByMsgId(Context context, String msgId,
			FindListener<CommentBean> findListener) {
		BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
		query.addWhereEqualTo("msgBeanId", msgId);
		query.findObjects(context, findListener);
	}
}

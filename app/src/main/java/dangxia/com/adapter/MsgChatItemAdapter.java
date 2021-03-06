package dangxia.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;

import java.util.List;
import java.util.ListIterator;

import dangxia.com.R;
import dangxia.com.dto.MessageDto;
import dangxia.com.utils.http.UrlHandler;
import de.hdodenhof.circleimageview.CircleImageView;

public class MsgChatItemAdapter extends RecyclerView.Adapter{

    private List<MessageDto> msgList;

    public List<MessageDto> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<MessageDto> msgList) {
        ListIterator<MessageDto> iterator = msgList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getType() == -1) {
                iterator.remove();
            }
        }
        this.msgList = msgList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_accept,parent,false);
                return new MsgHolder(v);
            case 0:
            case 3:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_send,parent,false);
                return new MsgHolder(v);
            default:return null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MsgHolder msgHolder = (MsgHolder)holder;
        MessageDto bean = msgList.get(position);
        if (bean.getType() == -1) {
            msgHolder.wholeView.setVisibility(View.GONE);
            return;
        }
        //填充信息内容
        msgHolder.contentTxt.setText(bean.getContent());
        //填充时间
        msgHolder.dateTxt.setText(bean.getDate());
        //填充头像
        // TODO: 2017/11/26 动态适配头像
        if (bean.getSender() == 2) {
            msgHolder.headerImg.setImageResource(R.mipmap.doge);
        } else {
            msgHolder.headerImg.setImageResource(R.mipmap.doge2);
            Log.i("123", "");
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (msgList.get(position).getType() == -1) return 3;
        if (msgList.get(position).getSender() == UrlHandler.getUserId()) {
            return 0;
        } else {
            return 1;
        }
    }

    private class MsgHolder extends RecyclerView.ViewHolder {
        TextView dateTxt;
        BubbleTextView contentTxt;
        CircleImageView headerImg;
        View wholeView;
        MsgHolder(View itemView) {
            super(itemView);
            wholeView = itemView.findViewById(R.id.whole_view);
            dateTxt = itemView.findViewById(R.id.chat_item_date);
            contentTxt = itemView.findViewById(R.id.chat_item_content_image);
            headerImg = itemView.findViewById(R.id.chat_item_header);
        }
    }

}

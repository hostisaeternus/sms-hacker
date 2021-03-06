package stage.metasploit.com.backdooredapk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Grunt on 15/02/2017.
 */

class SMSListAdapter extends RecyclerView.Adapter<SMSListAdapter.SMSListViewHolder> {
    private List<Conversation> conversations;
    private RecyclerView rv;

    List<Conversation> getConversations() {
        return conversations;
    }

    SMSListAdapter(List<Conversation> actualities, RecyclerView rv) {
        this.conversations = actualities;
        this.rv = rv;
    }

    @Override
    public SMSListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new SMSListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SMSListViewHolder holder, final int position) {
        holder.tvContactName.setText(conversations.get(position).getPerson());
        holder.tvLastMessage.setText(conversations.get(position).getLastMessage());
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    static class SMSListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContactName;
        private TextView tvLastMessage;

        SMSListViewHolder(View itemView) {
            super(itemView);
            this.tvContactName = (TextView) itemView.findViewById(R.id.sms_person_name);
            this.tvLastMessage = (TextView) itemView.findViewById(R.id.sms_last_message);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    void updateLastMessage(String number, String message, String name, String threadId) {
        int i = 0;
        for (Conversation conv: conversations) {
            if (conv.getPhoneNumber().compareTo(number) == 0) {
                conv.setLastMessage(message);
                conversations.remove(conv);
                notifyItemRemoved(i);
                conversations.add(0, conv);
                notifyItemInserted(0);
                rv.smoothScrollToPosition(0);
                return;
            }
            ++ i;
        }
        conversations.add(0, new Conversation(name, message, threadId, number));
        notifyItemInserted(0);
        rv.smoothScrollToPosition(0);
    }
}

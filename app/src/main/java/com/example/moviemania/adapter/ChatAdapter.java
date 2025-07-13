package com.example.moviemania.adapter;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemania.R;
import com.example.moviemania.classes.ChatMessage;
import io.noties.markwon.Markwon;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 1;
    private static final int TYPE_BOT = 2;
    private static final int TYPE_LOADING = 3;

    private List<ChatMessage> messages = new ArrayList<>();
    private Markwon markwon;
    private Handler typingHandler = new Handler(Looper.getMainLooper());

    // Add message count listener interface and variable
    public interface OnMessageCountChangedListener {
        void onMessageCountChanged(int count);
    }

    private OnMessageCountChangedListener messageCountListener;

    public void setMarkwon(Markwon markwon) {
        this.markwon = markwon;
    }

    public void setOnMessageCountChangedListener(OnMessageCountChangedListener listener) {
        this.messageCountListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        if (message.isLoading()) return TYPE_LOADING;
        return message.isUser() ? TYPE_USER : TYPE_BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_USER:
                View userView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_message, parent, false);
                return new UserMessageViewHolder(userView);
            case TYPE_LOADING:
                View loadingView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_loading_message, parent, false);
                return new LoadingMessageViewHolder(loadingView);
            default:
                View botView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_bot_message, parent, false);
                return new BotMessageViewHolder(botView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);

        if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).bind(message);
        } else if (holder instanceof BotMessageViewHolder) {
            ((BotMessageViewHolder) holder).bind(message, markwon);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
        notifyMessageCountChanged();
    }

    public void addLoadingMessage() {
        messages.add(new ChatMessage(true));
        notifyItemInserted(messages.size() - 1);
        notifyMessageCountChanged();
    }

    public void removeLoadingMessage() {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).isLoading()) {
                messages.remove(i);
                notifyItemRemoved(i);
                notifyMessageCountChanged();
                break;
            }
        }
    }

    public void addTypingMessage(String fullMessage, OnTypingCompleteListener listener) {
        ChatMessage typingMessage = new ChatMessage(fullMessage, true, true);
        messages.add(typingMessage);
        int position = messages.size() - 1;
        notifyItemInserted(position);
        notifyMessageCountChanged();

        startTypingAnimation(typingMessage, position, listener);
    }

    private void startTypingAnimation(ChatMessage message, int position, OnTypingCompleteListener listener) {
        String fullText = message.getFullMessage();
        String[] words = fullText.split(" ");
        final int[] currentWordIndex = {0};
        final int typingSpeed = 150; // 150ms per word

        Runnable typingRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentWordIndex[0] < words.length) {
                    StringBuilder partialText = new StringBuilder();
                    for (int i = 0; i <= currentWordIndex[0]; i++) {
                        partialText.append(words[i]);
                        if (i < currentWordIndex[0]) {
                            partialText.append(" ");
                        }
                    }

                    message.setMessage(partialText.toString());
                    notifyItemChanged(position);

                    currentWordIndex[0]++;

                    if (currentWordIndex[0] < words.length) {
                        typingHandler.postDelayed(this, typingSpeed);
                    } else {
                        // Typing complete
                        message.setTyping(false);
                        message.setMessage(fullText); // Ensure complete text
                        notifyItemChanged(position);
                        if (listener != null) {
                            listener.onTypingComplete();
                        }
                    }
                }
            }
        };

        typingHandler.post(typingRunnable);
    }

    public void clearMessages() {
        messages.clear();
        notifyDataSetChanged();
        notifyMessageCountChanged();
    }

    private void notifyMessageCountChanged() {
        if (messageCountListener != null) {
            messageCountListener.onMessageCountChanged(messages.size());
        }
    }

    public interface OnTypingCompleteListener {
        void onTypingComplete();
    }

    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
        }
    }

    static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        BotMessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }

        void bind(ChatMessage message, Markwon markwon) {
            if (message.isTyping()) {
                // For typing messages, show partial text with cursor
                String displayText = message.getMessage();
                if (message.getMessage().length() < message.getFullMessage().length()) {
                    displayText += "|"; // Add typing cursor
                }

                if (markwon != null) {
                    markwon.setMarkdown(messageText, displayText);
                } else {
                    messageText.setText(displayText);
                }
            } else {
                // For complete messages, show full formatted text
                if (markwon != null) {
                    markwon.setMarkdown(messageText, message.getMessage());
                } else {
                    messageText.setText(message.getMessage());
                }
            }
        }
    }

    static class LoadingMessageViewHolder extends RecyclerView.ViewHolder {
        LoadingMessageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
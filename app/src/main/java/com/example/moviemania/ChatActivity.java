package com.example.moviemania;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemania.adapter.ChatAdapter;
import com.example.moviemania.api.ApiInterface;
import com.example.moviemania.api.RetroInstance;
import com.example.moviemania.classes.ChatMessage;
import com.example.moviemania.classes.ChatRequest;
import com.example.moviemania.classes.ChatResponse;

import io.noties.markwon.Markwon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private Button buttonSend;
    private ChatAdapter chatAdapter;
    private ApiInterface apiInterface;
    private String currentSessionId;
    private View layoutChatPlaceholder; // Add this


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setupViews();
        setupRecyclerView();
        setupRetrofit();
        setupClickListeners();
    }

    private void setupViews() {
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        layoutChatPlaceholder = findViewById(R.id.layoutChatPlaceholder); // Add this

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Movie Chat");
        }
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter();

        // Initialize Markwon for markdown rendering
        Markwon markwon = Markwon.create(this);
        chatAdapter.setMarkwon(markwon);

        // Add message count listener to control placeholder visibility
        chatAdapter.setOnMessageCountChangedListener(new ChatAdapter.OnMessageCountChangedListener() {
            @Override
            public void onMessageCountChanged(int count) {
                updatePlaceholderVisibility(count);
            }
        });

        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);
    }

    // Add this method
    private void updatePlaceholderVisibility(int messageCount) {
        if (messageCount == 0) {
            layoutChatPlaceholder.setVisibility(View.VISIBLE);
            recyclerViewChat.setVisibility(View.GONE);
        } else {
            layoutChatPlaceholder.setVisibility(View.GONE);
            recyclerViewChat.setVisibility(View.VISIBLE);
        }
    }
    private void setupRetrofit() {
        apiInterface = RetroInstance.getChatApiInterface(); // Use chat API
    }

    private void setupClickListeners() {
        buttonSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (message.isEmpty()) return;

        editTextMessage.setText("");
        chatAdapter.addMessage(new ChatMessage(message, true));

        // Add loading message
        chatAdapter.addLoadingMessage();
        recyclerViewChat.scrollToPosition(chatAdapter.getItemCount() - 1);

        ChatRequest request = new ChatRequest(message, currentSessionId);
        Call<ChatResponse> call = apiInterface.sendChatMessage(request);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                // Remove loading message
                chatAdapter.removeLoadingMessage();

                if (response.isSuccessful() && response.body() != null) {
                    ChatResponse chatResponse = response.body();
                    currentSessionId = chatResponse.getSession_id();

                    // Add typing message with animation
                    chatAdapter.addTypingMessage(chatResponse.getResponse(), new ChatAdapter.OnTypingCompleteListener() {
                        @Override
                        public void onTypingComplete() {
                            // Scroll to bottom when typing is complete
                            recyclerViewChat.scrollToPosition(chatAdapter.getItemCount() - 1);
                        }
                    });

                    recyclerViewChat.scrollToPosition(chatAdapter.getItemCount() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Error sending message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                // Remove loading message on failure
                chatAdapter.removeLoadingMessage();
                Toast.makeText(ChatActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_chat) {
            deleteCurrentSession();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentSession() {
        if (currentSessionId != null) {
            Call<Void> call = apiInterface.deleteSession(currentSessionId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        chatAdapter.clearMessages();
                        currentSessionId = null;
                        Toast.makeText(ChatActivity.this, "Chat deleted", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ChatActivity.this, "Failed to delete chat", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
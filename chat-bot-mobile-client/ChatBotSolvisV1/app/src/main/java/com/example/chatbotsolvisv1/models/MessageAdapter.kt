package com.example.chatbotsolvisv1.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotsolvisv1.R

class MessageAdapter(public val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.messageText)

        fun bind(message: Message) {
            messageText.text = message.content

            val params = messageText.layoutParams as RelativeLayout.LayoutParams
            if (message.sessionId != "bot") {
                params.addRule(RelativeLayout.ALIGN_PARENT_END)
                params.removeRule(RelativeLayout.ALIGN_PARENT_START)
                messageText.setBackgroundResource(R.drawable.bot_message_background)
                messageText.setTextColor(itemView.context.getColor(R.color.white))

            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_START)
                params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                messageText.setBackgroundResource(R.drawable.user_message_background)
            }
            messageText.layoutParams = params
        }
    }
}


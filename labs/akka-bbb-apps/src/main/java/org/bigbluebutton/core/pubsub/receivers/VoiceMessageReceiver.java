package org.bigbluebutton.core.pubsub.receivers;

import org.bigbluebutton.common.messages.MessagingConstants;
import org.bigbluebutton.core.api.IBigBlueButtonInGW;
import org.bigbluebutton.core.pubsub.redis.MessageHandler;
import org.bigbluebutton.core.service.voice.VoiceKeyUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VoiceMessageReceiver implements MessageHandler {

	private IBigBlueButtonInGW bbbGW;
	
	public VoiceMessageReceiver(IBigBlueButtonInGW bbbGW) {
		this.bbbGW = bbbGW;
	}

	@Override
	public void handleMessage(String pattern, String channel, String message) {
		if (channel.equalsIgnoreCase(MessagingConstants.TO_VOICE_CHANNEL)) {

			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse(message);
			JsonObject headerObject = (JsonObject) obj.get("header");
			JsonObject payloadObject = (JsonObject) obj.get("payload");

			String eventName = headerObject.get("name").toString().replace("\"", "");

			if (eventName.equalsIgnoreCase(MessagingConstants.MUTE_USER_REQUEST)){

				String meetingID = payloadObject.get("meeting_id").toString().replace("\"", "");
				String requesterID = payloadObject.get("requester_id").toString().replace("\"", "");
				String userID = payloadObject.get("userid").toString().replace("\"", "");
				String muteString = payloadObject.get(VoiceKeyUtil.MUTE).toString().replace("\"", "");
				Boolean mute = Boolean.valueOf(muteString);

				System.out.println("handling mute_user_request");
				bbbGW.muteUser(meetingID, requesterID, userID, mute);
			}
			else if (eventName.equalsIgnoreCase(MessagingConstants.USER_LEFT_VOICE_REQUEST)){

				String meetingID = payloadObject.get("meeting_id").toString().replace("\"", "");
				String userID = payloadObject.get("userid").toString().replace("\"", "");

				System.out.println("handling user_left_voice_request");
				bbbGW.voiceUserLeft(meetingID, userID);
			}
		}
	}
}

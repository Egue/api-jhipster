package com.comunicamosmas.api.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class MailRelaySendMail {

	//private static final long serialVersionUID = 1L;

	public class Send {

		private From from;

		private List<To> to;

		private String subject;

		private String html_part;

		private String text_part;

		private boolean txt_part_auto;

		private Headers headers;

		private List<String> smtp_tags;

		private List<Attachment> attachments;

		public From getFrom() {
			return from;
		}

		public void setFrom(From from) {
			this.from = from;
		}

		public List<To> getTo() {
			return to;
		}

		public void setTo(List<To> to) {
			this.to = to;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getHtml_part() {
			return html_part;
		}

		public void setHtml_part(String html_part) {
			this.html_part = html_part;
		}

		public String getText_part() {
			return text_part;
		}

		public void setText_part(String text_part) {
			this.text_part = text_part;
		}

		public boolean isTxt_part_auto() {
			return txt_part_auto;
		}

		public void setTxt_part_auto(boolean txt_part_auto) {
			this.txt_part_auto = txt_part_auto;
		}

		public Headers getHeaders() {
			return headers;
		}

		public void setHeaders(Headers headers) {
			this.headers = headers;
		}

		public List<String> getSmtp_tags() {
			return smtp_tags;
		}

		public void setSmtp_tags(List<String> smtp_tags) {
			this.smtp_tags = smtp_tags;
		}

		public List<Attachment> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<Attachment> attachments) {
			this.attachments = attachments;
		}

		@Override
		public String toString() {
			return "Send [from=" + from + ", to=" + to + ", subject=" + subject + ", html_part=" + html_part
					+ ", text_part=" + text_part + ", txt_part_auto=" + txt_part_auto + ", headers=" + headers
					+ ", smtp_tags=" + smtp_tags + ", attachments=" + attachments + "]";
		}
		
		
	}

	public class From {
		private String email;

		private String name;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class To {

		private String email;
		private String name;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class Headers {
		@JsonProperty("X-CustomHeader")
		private String customHeader;

		public String getCustomHeader() {
			return customHeader;
		}

		public void setCustomHeader(String customHeader) {
			this.customHeader = customHeader;
		}

	}

	public class Attachment {
		private String content;
		private String file_name;
		private String content_type;
		private String content_id;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getFile_name() {
			return file_name;
		}

		public void setFile_name(String file_name) {
			this.file_name = file_name;
		}

		public String getContent_type() {
			return content_type;
		}

		public void setContent_type(String content_type) {
			this.content_type = content_type;
		}

		public String getContent_id() {
			return content_id;
		}

		public void setContent_id(String content_id) {
			this.content_id = content_id;
		}

	}
}
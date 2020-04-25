package com.pc.model.stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;

@Entity
@IdClass(value = News.NewsId.class)
public class News {
	

	@Id
	private String title;

	@Id
	private LocalDateTime publishedAt;
	
	private String author;
	private String description;
	private String url;
	private String urlToImage;
	private String source;

	@Lob
	private String content;
	
	private String searchWord;
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublistedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Embeddable
	public static class NewsId implements Serializable {
		String title;
		LocalDateTime publishedAt;
		
		public NewsId(){
			super();
		}
		
		public NewsId(String title, LocalDateTime publishedAt) {
			this.title = title;
			this.publishedAt = publishedAt;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public LocalDateTime getPublishedAt() {
			return publishedAt;
		}

		public void setPublishedAt(LocalDateTime publishedAt) {
			this.publishedAt = publishedAt;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object == null)
				return false;

			if (getClass() != object.getClass())
				return false;

			NewsId that = (NewsId) object;

			if (Objects.equals(getTitle(), that.title)
					&& Objects.equals(getPublishedAt(), that.publishedAt))
				return true;
			else
				return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(getTitle(), getPublishedAt());
		}

	}

}

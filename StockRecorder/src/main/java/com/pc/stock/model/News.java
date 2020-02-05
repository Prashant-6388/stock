package com.pc.stock.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(value = News.NewsId.class)
public class News {
	@Id
	String author;

	@Id
	String title;

	@Id
	LocalDateTime publishedAt;

	String description;

	String url;

	String urlToImage;


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

	public LocalDateTime getPublistedAt() {
		return publishedAt;
	}

	public void setPublistedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public static class NewsId implements Serializable {
		String author;
		String title;
		LocalDateTime publishedAt;
		
		public NewsId(){
			super();
		}
		
		public NewsId(String author, String title, LocalDateTime publishedAt) {
			this.author = author;
			this.title = title;
			this.publishedAt = publishedAt;
		}

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

			if (Objects.equals(getAuthor(), that.author) && Objects.equals(getTitle(), that.title)
					&& Objects.equals(getPublishedAt(), that.publishedAt))
				return true;
			else
				return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(getAuthor(), getTitle(), getPublishedAt());
		}

	}

}

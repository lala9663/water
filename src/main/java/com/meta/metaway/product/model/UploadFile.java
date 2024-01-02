package com.meta.metaway.product.model;

import lombok.Data;

	@Data
	public class UploadFile {
		private long fileId;
		private long productId;
		private String uuidFileName;
		private String fileOriginalName;
		private String filePath;
		private long fileSize;
	}

package dk.kvalitetsit.consent.admingui;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequest {
	
	private MultipartFile pdfFile ;
	
	private String appId;

	private String municipality;

	public MultipartFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(MultipartFile pdfFile) {
		this.pdfFile = pdfFile;
	}

	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}

package cs454.seo;

public class tempData {

	private String url;
	private int depth;
	private boolean isCrawled;
	private boolean isSameDomain;

	public tempData(String url, int depth, boolean isCrawled,
			boolean isSameDomain) {
		this.url = url;
		this.depth = depth;
		this.isCrawled = isCrawled;
		this.isSameDomain = isSameDomain;
	}

	public tempData(boolean isCrawled) {

		this.isCrawled = isCrawled;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isCrawled() {
		return isCrawled;
	}

	public void setCrawled(boolean isCrawled) {
		this.isCrawled = isCrawled;
	}

	public boolean isSameDomain() {
		return isSameDomain;
	}

	public void setSameDomain(boolean isSameDomain) {
		this.isSameDomain = isSameDomain;
	}

}

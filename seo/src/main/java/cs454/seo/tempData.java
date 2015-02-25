package cs454.seo;

public class tempData {

	private String url;
	private int depth;
	private boolean isCrawled;

	public tempData(String url, int depth, boolean isCrawled) {
		this.url = url;
		this.depth = depth;
		this.isCrawled = isCrawled;
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

}

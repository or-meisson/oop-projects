public class RendererFactory {
	private Renderer renderer;

	public RendererFactory() {
		this.renderer = null;
	}
	public Renderer buildRenderer (String type, int size){
		switch (type.toLowerCase()) {
			case "whatever":
				renderer = new VoidRenderer();
				break;
			case "console":
				renderer = new ConsoleRenderer(size);
				break;
		}
		return renderer;
	}
}

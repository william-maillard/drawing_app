package dessin;

public interface Zoomable {
	
	/**
	 * Zoom sur la forme courrante
	 * @param zoomX le niveau de zoom
	 * @return <ul>
	 * 				<li>true si le zoom a pu etre effectue</li>
	 * 				<li>false sinon</li>
	 * 		   </ul>
	 */
	public boolean zoom(int zoomX);
}

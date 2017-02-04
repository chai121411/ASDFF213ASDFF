package sl.model;

public class Song {
	private String songName;
	private String artist;
	private String album;
	private int year;
	
	public Song(String songName, String artist, String album, int year) {
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public Song(String songName, String artist, String album) {
		this(songName, artist, album, 0);
	}
	
	public Song(String songName, String artist, int year) {
		this(songName, artist, null, year);
	}
	
	public Song(String songName, String artist) {
		this(songName, artist, null, 0);
	}
	
	public boolean equals(Object o) { //If the name and artist are the same as an existing song, the add should not be allowed
		if (o == null || !(o instanceof Song)) {
			return false;
		}
		Song other = (Song)o;
		return songName == other.songName && artist == other.artist;
	} 

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

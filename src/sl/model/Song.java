package sl.model;

import java.util.Comparator;

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
		this(songName, artist, album, -1);
	}
	
	public Song(String songName, String artist, int year) {
		this(songName, artist, null, year);
	}
	
	public Song(String songName, String artist) {
		this(songName, artist, null, -1);
	}
	
	public static class Comp implements Comparator<Song> {
		@Override
		public int compare(Song o1, Song o2) {
			String songname1 = o1.getSongName();
			String songname2 = o2.getSongName();
			return songname1.compareToIgnoreCase(songname2);
		}
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

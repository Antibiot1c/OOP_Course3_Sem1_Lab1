import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

class Song {
    private String title;
    private String artist;
    private int durationInSeconds;
    private String genre;

    public Song(String title, String artist, int durationInSeconds, String genre) {
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getGenre() {
        return genre;
    }
}

class Album {
    private String title;
    private List<Song> songs;

    public Album(String title, List<Song> songs) {
        this.title = title;
        this.songs = songs;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        int totalDuration = 0;
        for (Song song : songs) {
            totalDuration += song.getDurationInSeconds();
        }
        return totalDuration;
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = songs.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Song temp = songs.get(i);
            songs.set(i, songs.get(j));
            songs.set(j, temp);
        }
    }

    public List<Song> getSongs() {
        return songs;
    }
}

class Disk {
    private String title;
    private List<Album> albums;

    public Disk(String title, List<Album> albums) {
        this.title = title;
        this.albums = albums;
    }

    public void shuffle() {
        for (Album album : albums) {
            album.shuffle();
        }
    }
}

class MusicCollection {
    private List<Album> albums;

    public MusicCollection(List<Album> albums) {
        this.albums = albums;
    }

    public Song findSongByDuration(int minDuration, int maxDuration) {
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                int duration = song.getDurationInSeconds();
                if (duration >= minDuration && duration <= maxDuration) {
                    return song;
                }
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        // Створення альбомів, пісень і диска
        List<Song> songs1 = new ArrayList<>();
        songs1.add(new Song("Wake Up", "Smash Into Pieces", 220, "Rock"));
        songs1.add(new Song("Arcadia", "Smash Into Pieces", 172, "Rock"));
        songs1.add(new Song("Everything They S4Y", "Smash Into Pieces", 198, "Rock"));
        songs1.add(new Song("All Eyes on You", "Smash Into Pieces", 188, "Rock"));
        songs1.add(new Song("Forever Alone", "Smash Into Pieces", 189, "Rock"));
        Album album1 = new Album("Arcadia", songs1);

        List<Song> songs2 = new ArrayList<>();
        songs2.add(new Song("Gasoline", "The Weeknd", 213, "Pop"));
        songs2.add(new Song("Take My Breath", "The Weeknd", 340, "Pop"));
        songs2.add(new Song("Sacrifice", "The Weeknd", 189, "Pop"));
        songs2.add(new Song("Out of Time", "The Weeknd", 215, "Pop"));
        Album album2 = new Album("Dawn FM", songs2);

        List<Album> albums = new ArrayList<>();
        albums.add(album1);
        albums.add(album2);

        Disk disk = new Disk("My Music Disk", albums);
        MusicCollection musicCollection = new MusicCollection(albums);

        // Реалізуйте консольне меню для взаємодії з користувачем
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Меню:");
            System.out.println("1. Знайти пісню за діапазоном тривалості");
            System.out.println("2. Перемішати альбом");
            System.out.println("3. Показати всі альбоми і пісні");
            System.out.println("4. Завершити");
            System.out.print("Ваш вибір: ");
            choice = scanner.nextInt();
        
            switch (choice) {
                case 1:
                    System.out.print("Мінімальна тривалість (с): ");
                    int minDuration = scanner.nextInt();
                    System.out.print("Максимальна тривалість (с): ");
                    int maxDuration = scanner.nextInt();
                    Song song = musicCollection.findSongByDuration(minDuration, maxDuration);
                    if (song != null) {
                        System.out.println("Знайдено пісню: " + song.getTitle());
                        System.out.println("-------------------------------------");
                    } else {
                        System.out.println("Пісню не знайдено.");
                        System.out.println("-------------------------------------");
                    }
                    break;
               case 2:
                    for (Album album : albums) {
                        Collections.shuffle(album.getSongs());
                    }
                    System.out.println("Альбоми були перемішані.");
                    System.out.println("-------------------------------------");
                    break;
                case 3:
                    System.out.println("Усі альбоми та пісні:");
                    for (Album album : albums) {
                        System.out.println("Альбом: " + album.getTitle()); 
                        List<Song> albumSongs = album.getSongs();
                       for (Song innerSong : albumSongs) {
                        System.out.println("  - " + innerSong.getTitle() + " (" + innerSong.getDurationInSeconds() + " с)");
                        }
                    }
                    System.out.println("-------------------------------------");
                    break;
                case 4:
                    System.out.println("Дякуємо за використання програми.");
                    System.out.println("-------------------------------------");
                    break;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    System.out.println("-------------------------------------");
            }
        } while (choice != 4);
    }
}

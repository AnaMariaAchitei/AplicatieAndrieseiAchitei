package application;

public class SongDAOFactory
{
	SongDAOMySQL_Impl createSongDAO()
	{
		return new SongDAOMySQL_Impl();
	}
}

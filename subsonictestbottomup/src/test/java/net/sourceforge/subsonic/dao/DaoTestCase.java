package net.sourceforge.subsonic.dao;

import junit.framework.TestCase;
import net.sourceforge.subsonic.domain.InternetRadio;
import net.sourceforge.subsonic.domain.Transcoding;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.Date;
import java.util.List;

public class DaoTestCase extends TestCase
{

	private DaoHelper daoHelper;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		File dbFile = new File( "./tmp" );

		delete( dbFile );

		daoHelper = new HsqlDaoHelper( new File( dbFile, "db" ).getAbsolutePath() );
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		getJdbcTemplate().execute( "shutdown" );
	}

	protected JdbcTemplate getJdbcTemplate()
	{
		return daoHelper.getJdbcTemplate();
	}

	private static void delete( File file )
	{
		if( file.isDirectory() )
		{
			for( File child : file.listFiles() )
			{
				delete( child );
			}
		}
		file.delete();
	}


	public void testInternetRadioDao()
	{
		getJdbcTemplate().execute( "delete from internet_radio" );

		InternetRadioDao internetRadioDao = new InternetRadioDao();
		internetRadioDao.setDaoHelper( daoHelper );

		// Test InternetRadioDao.createInternetRadio() && InternetRadioDao.getAllInternetRadios()
		InternetRadio radio = new InternetRadio( "name", "streamUrl", "homePageUrl", true, new Date() );
		internetRadioDao.createInternetRadio( radio );
		InternetRadio newRadio = internetRadioDao.getAllInternetRadios().get( 0 );
		assertInternetRadioEquals( radio, newRadio );

		// Test InternetRadioDao.updateInternetRadio()
		radio = newRadio;
		radio.setName( "newName" );
		radio.setStreamUrl( "newStreamUrl" );
		radio.setHomepageUrl( "newHomePageUrl" );
		radio.setEnabled( false );
		radio.setChanged( new Date( 234234L ) );
		internetRadioDao.updateInternetRadio( radio );
		newRadio = internetRadioDao.getAllInternetRadios().get( 0 );
		assertInternetRadioEquals( radio, newRadio );

		// Test InternetRadioDao.deleteInternetRadio()
		internetRadioDao.deleteInternetRadio( newRadio.getId() );
		assertEquals( internetRadioDao.getAllInternetRadios().size(), 0 );
	}

	private void assertInternetRadioEquals( InternetRadio expected, InternetRadio actual )
	{
		assertEquals( "Wrong name.", expected.getName(), actual.getName() );
		assertEquals( "Wrong stream url.", expected.getStreamUrl(), actual.getStreamUrl() );
		assertEquals( "Wrong home page url.", expected.getHomepageUrl(), actual.getHomepageUrl() );
		assertEquals( "Wrong enabled state.", expected.isEnabled(), actual.isEnabled() );
		assertEquals( "Wrong changed date.", expected.getChanged(), actual.getChanged() );
	}

	public void testTranscodingDao()
	{
		getJdbcTemplate().execute( "delete from transcoding2" );
		getJdbcTemplate().execute( "delete from player_transcoding2" );
		getJdbcTemplate().execute( "delete from player" );

		TranscodingDao transcodingDao = new TranscodingDao();
		transcodingDao.setDaoHelper( daoHelper );

		// Test TranscodingDao.createTranscoding() and TranscodingDao.getAllTranscodings()
		Transcoding transcoding = new Transcoding( null, "name",
				"sourceFormats", "targetFormat",
				"step1", "step2", "step3", false );
		transcodingDao.createTranscoding( transcoding );
		Transcoding newTranscoding = transcodingDao.getAllTranscodings().get( 0 );
		assertTranscodingEquals( transcoding, newTranscoding );

		// Test TranscodingDao.updateTranscoding()
		transcoding = newTranscoding;
		transcoding.setName( "newName" );
		transcoding.setSourceFormats( "newSourceFormats" );
		transcoding.setTargetFormat( "newTargetFormat" );
		transcoding.setStep1( "newStep1" );
		transcoding.setStep2( "newStep2" );
		transcoding.setStep3( "newStep3" );
		transcoding.setDefaultActive( true );
		transcodingDao.updateTranscoding( transcoding );
		newTranscoding = transcodingDao.getAllTranscodings().get( 0 );
		assertTranscodingEquals( transcoding, newTranscoding );

		// Test TranscodingDao.setTranscodingsForPlayer() & TranscodingDao.getTranscodingsForPlayer()
		// Insert a test player
		getJdbcTemplate().execute( "insert into player (id, name, type, username, ip_address, auto_control_enabled, " +
				"last_seen, cover_art_scheme, transcode_scheme, dynamic_ip, technology, client_id) " +
				"values (1, 'name', 'type', 'username', 'ip', true, 0, 0, 0, false, 0, 'android')" );
		transcoding = newTranscoding;
		transcodingDao.setTranscodingsForPlayer( "1", new int[]{transcoding.getId()} );
		List<Transcoding> transcodings = transcodingDao.getTranscodingsForPlayer( "1" );
		assertEquals( transcodings.size(), 1 );
		assertTranscodingEquals( transcoding, transcodings.get( 0 ) );

		// Test TranscodingDao.deleteTranscoding()
		transcodingDao.deleteTranscoding( transcoding.getId() );
		assertEquals( transcodingDao.getAllTranscodings().size(), 0 );
	}

	private void assertTranscodingEquals( Transcoding expected, Transcoding actual )
	{
		assertEquals( "Wrong name.", expected.getName(), actual.getName() );
		assertEquals( "Wrong source formats.", expected.getSourceFormats(), actual.getSourceFormats() );
		assertEquals( "Wrong target format.", expected.getTargetFormat(), actual.getTargetFormat() );
		assertEquals( "Wrong step 1.", expected.getStep1(), actual.getStep1() );
		assertEquals( "Wrong step 2.", expected.getStep2(), actual.getStep2() );
		assertEquals( "Wrong step 3.", expected.getStep3(), actual.getStep3() );
		assertEquals( "Wrong default active.", expected.isDefaultActive(), actual.isDefaultActive() );
	}
}

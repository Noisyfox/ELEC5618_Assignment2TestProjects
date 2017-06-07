package net.sourceforge.subsonic.dao;

import junit.framework.TestCase;
import net.sourceforge.subsonic.domain.InternetRadio;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.Date;

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
		InternetRadioDao internetRadioDao = new InternetRadioDao();
		internetRadioDao.setDaoHelper( daoHelper );

		// Test InternetRadioDao.createInternetRadio()
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
}

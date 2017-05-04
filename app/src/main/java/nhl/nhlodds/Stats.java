package nhl.nhlodds;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class Stats extends ActionBarActivity {
    public Team selected_team = new Team();
    public boolean help = false;
    public String team, abbreviation, games_played, wins, losses, rank, points, streak;
    public int logo;

    private String games_played_query, wins_query, losses_query, overtime_losses_query, rank_query, points_query, streak_query;

    int logos[] = { R.mipmap.ana, R.mipmap.phx, R.mipmap.bos, R.mipmap.buf, R.mipmap.cgy, R.mipmap.car, R.mipmap.chi, R.mipmap.col, R.mipmap.cbj, R.mipmap.dal,
                    R.mipmap.det, R.mipmap.edm, R.mipmap.fla, R.mipmap.lak, R.mipmap.min, R.mipmap.mtl, R.mipmap.nsh, R.mipmap.njd, R.mipmap.nyi, R.mipmap.nyr,
                    R.mipmap.ott, R.mipmap.phi, R.mipmap.pit, R.mipmap.stl, R.mipmap.sjs, R.mipmap.tbl, R.mipmap.tor, R.mipmap.van, R.mipmap.wsh, R.mipmap.wpg };

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        final TextView help_text = (TextView) findViewById(R.id.help_text);
        help_text.setVisibility(View.GONE);



        ImageButton help_button = (ImageButton) findViewById(R.id.help_button);

        selected_team = getIntent().getParcelableExtra("selected_team");

        final ImageView team_logo = (ImageView) findViewById(R.id.team_logo);
        team_logo.setImageResource(logos[selected_team.get_logo()]);

        final TextView team_name = (TextView) findViewById(R.id.team_name);
        team_name.setText(selected_team.get_name());

        final TextView gp = (TextView) findViewById(R.id.gp);
        gp.setText(selected_team.get_games_played());

        final TextView w = (TextView) findViewById(R.id.w);
        w.setText(selected_team.get_wins());

        final TextView l_otl = (TextView) findViewById(R.id.l_otl);
        l_otl.setText(selected_team.get_losses());

        final TextView rnk = (TextView) findViewById(R.id.rnk);
        rnk.setText(selected_team.get_rank());

        final TextView p = (TextView) findViewById(R.id.p);
        p.setText(selected_team.get_points());

        final TextView strk = (TextView) findViewById(R.id.strk);
        strk.setText(selected_team.get_streak());

        final TextView elo = (TextView) findViewById(R.id.elo);
        elo.setText(selected_team.get_elo());

        final TextView prob = (TextView) findViewById(R.id.prob);
        prob.setText(selected_team.get_probability());

        elo.setText("3211");
        prob.setText("100.0%");

        mData.add(new GameEntity(selected_team.get_abbreviation(), R.drawable.chi_436_5, R.string.team1_name, R.mipmap.buf)); //7
        mData.add(new GameEntity(selected_team.get_abbreviation(), R.drawable.chi_436_4, R.string.team1_name, R.mipmap.buf)); //6
        mData.add(new GameEntity(selected_team.get_abbreviation(), R.drawable.chi_436_3, R.string.team1_name, R.mipmap.buf)); //5
        mData.add(new GameEntity(selected_team.get_abbreviation(), R.drawable.chi_436_2, R.string.team1_name, R.mipmap.buf)); //5
        mData.add(new GameEntity(selected_team.get_abbreviation(), R.drawable.chi_436_1, R.string.team1_name, R.mipmap.buf)); //3

        final TextView gp_text = (TextView) findViewById(R.id.gp_text);
        final TextView w_text = (TextView) findViewById(R.id.w_text);
        final TextView l_otl_text = (TextView) findViewById(R.id.l_otl_text);
        final TextView rnk_text = (TextView) findViewById(R.id.rnk_text);
        final TextView p_text = (TextView) findViewById(R.id.p_text);
        final TextView strk_text = (TextView) findViewById(R.id.strk_text);
        final TextView elo_text = (TextView) findViewById(R.id.elo_text);
        final TextView prob_text = (TextView) findViewById(R.id.prob_text);

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (help) {
                    help = false;
                    gp.setVisibility(View.VISIBLE);
                    w.setVisibility(View.VISIBLE);
                    l_otl.setVisibility(View.VISIBLE);
                    rnk.setVisibility(View.VISIBLE);
                    p.setVisibility(View.VISIBLE);
                    strk.setVisibility(View.VISIBLE);
                    elo.setVisibility(View.VISIBLE);
                    prob.setVisibility(View.VISIBLE);
                    gp_text.setVisibility(View.VISIBLE);
                    w_text.setVisibility(View.VISIBLE);
                    l_otl_text.setVisibility(View.VISIBLE);
                    rnk_text.setVisibility(View.VISIBLE);
                    p_text.setVisibility(View.VISIBLE);
                    strk_text.setVisibility(View.VISIBLE);
                    elo_text.setVisibility(View.VISIBLE);
                    prob_text.setVisibility(View.VISIBLE);
                    help_text.setVisibility(View.GONE);
                } else {
                    help = true;
                    gp.setVisibility(View.GONE);
                    w.setVisibility(View.GONE);
                    l_otl.setVisibility(View.GONE);
                    rnk.setVisibility(View.GONE);
                    p.setVisibility(View.GONE);
                    strk.setVisibility(View.GONE);
                    elo.setVisibility(View.GONE);
                    prob.setVisibility(View.GONE);
                    gp_text.setVisibility(View.GONE);
                    w_text.setVisibility(View.GONE);
                    l_otl_text.setVisibility(View.GONE);
                    rnk_text.setVisibility(View.GONE);
                    p_text.setVisibility(View.GONE);
                    strk_text.setVisibility(View.GONE);
                    elo_text.setVisibility(View.GONE);
                    prob_text.setVisibility(View.GONE);
                    help_text.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coverflow_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

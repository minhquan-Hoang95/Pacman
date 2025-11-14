
package com.pacman.game.service;

import com.pacman.game.model.Maze;
import org.junit.Test;

import static org.junit.Assert.*;


public class MazeApiClientTest {

    @Test
    public void fetchMaze_integrationTest() {
        MazeApiClient client = new MazeApiClient();

        // Create a latch for async response, since LibGDX handles HTTP asynchronously
        final boolean[] success = {false};

        client.fetchMaze(new MazeApiClient.MazeCallback() {
            @Override
            public void onSuccess(Maze maze) {
                // validate the maze object returned
                assertNotNull(maze);
                success[0] = true;
            }

            @Override
            public void onError(String error) {
                fail("API call failed: " + error);
            }
        });

        // Wait for response (should use more sophisticated async handling in real test)
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        assertTrue("Maze should load successfully",success[0]);
    }
}

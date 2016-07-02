## What is this?

This is an elementary pong game engine library ([pong/](pong/)), together with a demo pong implementation using it ([pong-demo/](pong-demo/)). Included with the engine are also three AIs for enabling gameplay vs. the computer at different difficultty levels:

 - <b>Easy</b> - [TrackerAI](pong/src/com/rharel/pong/ai/TrackerAI.java): Simply follows the ball's position at all times.
 - <b>Medium</b> - [PredictorAI](pong/src/com/rharel/pong/ai/PredictorAI.java): Predicts the ball's movement trajectory in advance and plans accordingly.
 - <b>Hard</b> - [AggressiveAI](pong/src/com/rharel/pong/ai/AggressiveAI.java): Not only predicts the ball's movement trajectory, but also takes into account the opposing player's position and attempts to serve the ball away from it.


## License

This software is licensed under the **MIT License**. See the [LICENSE](LICENSE.txt) file for more information.
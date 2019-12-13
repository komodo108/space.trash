package bots;

import static main.Constants.KEY;

public class BasebotSingleton {
    private static BasebotSingleton ourInstance = new BasebotSingleton();
    public static BasebotSingleton getInstance() {
        return ourInstance;
    }

    private RealBasebot bot;

    private BasebotSingleton() { }

    public void setBot(RealBasebot bot, String key) {
        if(key.equals(KEY)) this.bot = bot;
    }

    public RealBasebot getBot() {
        return bot;
    }
}

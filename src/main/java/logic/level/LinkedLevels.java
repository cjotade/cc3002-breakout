package logic.level;

import java.util.LinkedList;

public class LinkedLevels extends LinkedList {
    private Level first;
    public LinkedLevels(){
        first = new NullLevel();
    }

    @Override
    public void addFirst(Object o) {
        super.addFirst(o);
    }

    @Override
    public Level getFirst() {
        return first;
    }

    public void addLast(Level level) {
        first.addPlayingLevel(level);
    }
}

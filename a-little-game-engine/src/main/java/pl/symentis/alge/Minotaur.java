package pl.symentis.alge;

import pl.symentis.alge.runtime.SDL_Rect;
import pl.symentis.alge.runtime.SDL_Surface;
import pl.symentis.alge.sprites.Sprite;
import pl.symentis.alge.sprites.SpriteSequence;

import java.util.Iterator;

public class Minotaur extends Sprite {

    public enum State {
        Idle,
        Attacking,
        Walking;
    }

    private final SpriteSequence spriteSequencewalking;
    private final SpriteSequence spriteSequenceAttacking;
    private final SpriteSequence spriteSequenceIdle;
    private final SDL_Rect rect;
    private State state;
    private SDL_Surface spriteSeqSurface;
    private Iterator<SDL_Surface> spriteSeqIter;

    public Minotaur(
            SpriteSequence spriteSequencewalking,
            SpriteSequence spriteSequenceAttacking,
            SpriteSequence spriteSequenceIdle,
            SDL_Rect rect) {
        this.spriteSequencewalking = spriteSequencewalking;
        this.spriteSequenceAttacking = spriteSequenceAttacking;
        this.spriteSequenceIdle = spriteSequenceIdle;
        this.rect = rect;
        this.spriteSeqIter = spriteSequenceIdle.loop();
        this.spriteSeqSurface = spriteSeqIter.next();
        this.state = State.Idle;
    }

    public void walkForward() {
        if (State.Walking.equals(state)) {
            rect.moveX();
            spriteSeqSurface = spriteSeqIter.next();
        } else {
            this.spriteSeqIter = spriteSequencewalking.loop();
            this.spriteSeqSurface = spriteSeqIter.next();
            this.state = State.Walking;
        }
    }

    public void attacking() {
        if (!State.Attacking.equals(state)) {
            this.spriteSeqIter = spriteSequenceAttacking.iterator();
            state = State.Attacking;
        }
    }

    public void update() {
        if (State.Attacking.equals(state)) {
            if (spriteSeqIter.hasNext()) {
                spriteSeqSurface = spriteSeqIter.next();
            } else {
                idle();
            }
        } else if (State.Idle.equals(state)) {
            spriteSeqSurface = spriteSeqIter.next();
        }
    }

    public void idle() {
        state = State.Idle;
        spriteSeqIter = spriteSequenceIdle.loop();
    }

    @Override
    public void draw(SDL_Surface sdlSurface) {
        sdlSurface.blitScaled(spriteSeqSurface, null, rect);
    }
}

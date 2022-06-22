package pl.symentis.alge;

import pl.symentis.alge.runtime.*;
import pl.symentis.alge.sprites.SpriteSequence;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Throwable {
        var runtime = SDLRuntime.getInstance();

        var window = runtime.createWindow("Game", 0, 0, 800, 600, 0);

        var surface = window.surface();

        SDL_PixelFormat pixelFormat = surface.pixelFormat();

        var spriteSequenceWalking = SpriteSequence.loadFrom(
                runtime, Paths.get("a-little-game-engine/src/main/resources/Minotaur_01/PNG Sequences/Walking/"));
        var spriteSequenceAttacking = SpriteSequence.loadFrom(
                runtime, Paths.get("a-little-game-engine/src/main/resources/Minotaur_01/PNG Sequences/Attacking/"));
        var spriteSequenceIdle = SpriteSequence.loadFrom(
                runtime, Paths.get("a-little-game-engine/src/main/resources/Minotaur_01/PNG Sequences/Idle/"));

        var rect = SDL_Rect.create(0, 0, 186, 128);
        var minotaur = new Minotaur(spriteSequenceWalking, spriteSequenceAttacking, spriteSequenceIdle, rect);

        var black = pixelFormat.SDL_MapRGB(0x00, 0x00, 0x00);
        var event = SDL_Event.empty();
        while (true) {
            var currentTimeMillis = System.currentTimeMillis();
            if (SDL_events.pollEvent(event)) {
                int type = event.type();
                if (type == SDL_events.SDL_QUIT) {
                    break;
                }
                if (type == SDL_events.SDL_KEYDOWN) {
                    if (event.keySymbolCode() == SDL_events.SDLK_RIGHT) {
                        minotaur.walkForward();
                    } else if (event.keySymbolCode() == SDL_events.SDLK_SPACE) {
                        minotaur.attacking();
                    }
                } else if (type == SDL_events.SDL_KEYUP) {
                    if (event.keySymbolCode() == SDL_events.SDLK_RIGHT) {
                        minotaur.idle();
                    }
                }
            }

            minotaur.update();

            surface.fillRect(null, black);
            minotaur.draw(surface);
            window.updateWindowSurface();
            var timeMillis = (1000 / 30) - (System.currentTimeMillis() - currentTimeMillis);
            if (timeMillis > 0) {
                Thread.sleep(timeMillis);
            }
        }
    }
}

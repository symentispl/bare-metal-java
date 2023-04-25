package pl.symentis.alge.sprites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import pl.symentis.alge.runtime.SDLRuntime;
import pl.symentis.alge.runtime.SDL_Surface;

public class SpriteSequence {
    private static Pattern pattern = Pattern.compile("(\\w+)_(\\d{2})_(\\w+)_(\\d{3})\\.(\\w+)");
    private static DecimalFormat decimalFormat = new DecimalFormat("000");

    public static SpriteSequence loadFrom(SDLRuntime runtime, Path dir) throws IOException {
        try (var files = Files.list(dir)) {
            var list = files.sorted((f1, f2) -> {
                        var fileName1 = f1.getFileName();
                        var fileName2 = f2.getFileName();

                        var number1 = extractSeqNumber(fileName1);
                        var number2 = extractSeqNumber(fileName2);
                        return number1.intValue() - number2.intValue();
                    })
                    .map(runtime::loadImage)
                    .toList();
            return new SpriteSequence(list);
        }
    }

    private final List<SDL_Surface> list;

    public SpriteSequence(List<SDL_Surface> list) {
        this.list = list;
    }

    public Iterator<SDL_Surface> loop() {
        return new CyclicIterator<>(list);
    }

    private static Number extractSeqNumber(Path fileName1) {
        var matcher1 = pattern.matcher(fileName1.toString());
        Number number1;
        if (matcher1.matches()) {
            try {
                return decimalFormat.parse(matcher1.group(4));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Iterator<SDL_Surface> iterator() {
        return list.iterator();
    }
}

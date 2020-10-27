package by.Andrey.jis3telegram.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    ARROW_DOWN(EmojiParser.parseToUnicode("&#127922;")),
    STATISTIC(EmojiParser.parseToUnicode("&#128202;")),
    STATISTIC_SHORT(EmojiParser.parseToUnicode("&#128200;")),
    STATISTIC_LONG(EmojiParser.parseToUnicode("&#128201;"));



    private String emojiName;

    @Override
    public String toString(){
        return emojiName;
    }
}

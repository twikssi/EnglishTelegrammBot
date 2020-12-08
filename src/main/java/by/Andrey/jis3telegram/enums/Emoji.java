package by.Andrey.jis3telegram.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public enum Emoji {
    RANDOM(EmojiParser.parseToUnicode("&#127922;")),
    STATISTIC(EmojiParser.parseToUnicode("&#128202;")),
    STATISTIC_SHORT(EmojiParser.parseToUnicode("&#128200;")),
    STATISTIC_LONG(EmojiParser.parseToUnicode("&#128201;")),
    BACK(EmojiParser.parseToUnicode("&#11013;")),
    SEARCH(EmojiParser.parseToUnicode("&#128269;")),
    SETUP(EmojiParser.parseToUnicode("&#9881;")),
    CLEVER(EmojiParser.parseToUnicode("&#127808;")),
    PIN(EmojiParser.parseToUnicode("&#128204;")),
    TILDA(EmojiParser.parseToUnicode("&#12336;")),
    QUESTION_AND(EmojiParser.parseToUnicode("&#8265;"));

    Emoji(String emojiName) {
        this.emojiName = emojiName;
    }

    private String emojiName;

    @Override
    public String toString(){
        return emojiName;
    }
}

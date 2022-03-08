package me.xmrvizzy.skyblocker.chat.filters;

import me.xmrvizzy.skyblocker.chat.ChatPatternListenerTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class AdFilterTest extends ChatPatternListenerTest<AdFilter> {
    public AdFilterTest() {
        super(new AdFilter());
    }

    @Test
    void noRank() {
        assertMatches("§7Advertiser§7: advertisement");
    }

    @Test
    void vip() {
        assertMatches("§a[VIP] Advertiser§f: advertisement");
    }

    @Test
    void mvp() {
        assertMatches("§b[MVP§c+§b] Advertiser§f: advertisement");
    }

    @Test
    void plusPlus() {
        assertMatches("§6[MVP§c++§6] Advertiser§f: advertisement");
    }

    @Test
    void capturesMessage() {
        assertGroup("§b[MVP§c+§b] b2dderr§f: buying prismapump", 2, "buying prismapump");
    }

    @Test
    void simpleAd() {
        assertFilters("§b[MVP§c+§b] b2dderr§f: buying prismapump");
    }

    @Test
    void uppercaseAd() {
        assertFilters("§a[VIP] Tecnoisnoob§f: SELLING REJUVENATE 5 Book on ah!");
    }

    @Test
    void characterSpam() {
        assertFilters("§a[VIP] Benyyy_§f: Hey, Visit my Island, i spent lots of time to build it! I also made donate room! <<<<<<<<<<<<<<<<<<<");
    }

    @Test
    void notAd() {
        Matcher matcher = listener.pattern.matcher("§a[VIP] NotMatching§f: This message shouldn't match!");
        assertTrue(matcher.matches());
        assertFalse(listener.onMatch(null, matcher));
    }

    void assertFilters(String message) {
        Matcher matcher = listener.pattern.matcher(message);
        assertTrue(matcher.matches());
        assertTrue(listener.onMatch(null, matcher));
    }
}
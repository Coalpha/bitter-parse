# FracCalc

- Class: "Hey make a Fractional Calculator"
- Me: "Oh that'll be easy, I'll just use the JavaScript Engine (Nashorn)"
- Class: "...You didn't really learn anything."
- Me: "That's the point"
- Class: "Do it without using Nashorn"
- Me: *poorly re-writes a JavaScript parser in Java*

### Usage (wtf_ja)

- Requires [`wtf_ja`](https://github.com/Coalpha/dotfiles/blob/master/bin/wtf_ja) and it's dependencies
- Basically, just install graalvm
- Run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -r -c:yes`
- Compile FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java --native:bin/FracCalc -c:yes`
- Compile and then run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -n -r -c:yes`

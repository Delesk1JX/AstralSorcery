#!/bin/bash
# Quick build script for Astral Sorcery 1.21.1 NeoForge

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Astral Sorcery Build Script ===${NC}"
echo -e "${YELLOW}Building Astral Sorcery for Minecraft 1.21.1 NeoForge${NC}\n"

# Check if SDKMAN is available
if [ -f "$HOME/.sdkman/bin/sdkman-init.sh" ]; then
    echo -e "${BLUE}Setting up SDKMAN environment...${NC}"
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    
    # Ensure correct Java and Gradle versions are available
    echo -e "${YELLOW}Checking Java/Gradle versions...${NC}"
    SDK_JAVA_HOME="/usr/local/sdkman/candidates/java/17.0.19-ms"
    SDK_GRADLE="/usr/local/sdkman/candidates/gradle/8.8/bin/gradle"
    
    if [ ! -d "$SDK_JAVA_HOME" ]; then
        echo -e "${YELLOW}Java 17 not found. Installing...${NC}"
        sdk install java 17.0.19-ms || echo "Java 17 already installed"
    fi
    
    if [ ! -f "$SDK_GRADLE" ]; then
        echo -e "${YELLOW}Gradle 8.8 not found. Installing...${NC}"
        sdk install gradle 8.8 || echo "Gradle 8.8 already installed"
    fi
    
    export JAVA_HOME="$SDK_JAVA_HOME"
    GRADLE_CMD="$SDK_GRADLE"
else
    echo -e "${YELLOW}SDKMAN not found. Using system Gradle/Java${NC}"
    GRADLE_CMD="gradle"
    
    # Verify Gradle version
    GRADLE_VERSION=$("$GRADLE_CMD" --version | grep "Gradle" | head -1)
    echo -e "${BLUE}Found: $GRADLE_VERSION${NC}"
fi

echo -e "${BLUE}Cleaning old build...${NC}"
rm -rf .gradle/caches/jars-* 2>/dev/null || true

echo -e "${BLUE}Running build: $GRADLE_CMD clean compileJava${NC}\n"
"$GRADLE_CMD" clean compileJava --no-daemon

echo -e "\n${GREEN}✓ Build configuration complete!${NC}"
echo -e "${YELLOW}Note: Compilation errors are expected due to Mixin/API migration work.${NC}"
echo -e "${YELLOW}See BUILD_INSTRUCTIONS.md for details on remaining work.${NC}"

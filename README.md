# BACnet HVAC DSL for PyDEVS Simulation

A Domain-Specific Language (DSL) for modeling BACnet HVAC systems and generating PyDEVS simulation code. This project enables Digital Twin development for building automation systems.

## 🚀 Quick Start

1. **Open Eclipse** with this workspace
2. **Run MWE2**: Right-click `GenerateSmartCityModel.mwe2` → Run As → MWE2 Workflow
3. **Test Generation**: Open `example.smartcitymodel` → Save (Ctrl+S)
4. **Check Output**: Look in `src-gen/` for generated Python files

📖 **First time?** Read [QUICK_REFERENCE.md](QUICK_REFERENCE.md) for step-by-step instructions.

---

## 📚 Documentation

| Document | Purpose | When to Use |
|----------|---------|-------------|
| **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** | Fast checklist and common operations | Starting setup, quick lookup |
| **[SETUP_GUIDE.md](SETUP_GUIDE.md)** | Detailed Eclipse setup instructions | First-time setup, troubleshooting |
| **[ECLIPSE_GUIDE.md](ECLIPSE_GUIDE.md)** | Visual guide to Eclipse interface | Finding menus, checking output |
| **[ARCHITECTURE.md](ARCHITECTURE.md)** | System architecture and data flow | Understanding how it works |
| **[FILE_SUMMARY.md](FILE_SUMMARY.md)** | Complete list of created files | Overview of project structure |

---

## 🏗️ Project Structure

```
DSL_BACnet/
├── iiit.ac.in.smartcitymodel/          # Main Xtext project
│   ├── src/
│   │   └── iiit/ac/in/smartcitymodel/
│   │       ├── SmartCityModel.xtext                    # Grammar definition
│   │       ├── GenerateSmartCityModel.mwe2             # Xtext workflow
│   │       ├── SmartCityModelStandaloneSetup.java      # Standalone setup
│   │       ├── SmartCityModelGeneratorLauncher.java    # CLI launcher
│   │       └── generator/
│   │           └── SmartCityModelGenerator.xtend       # Code generator ⭐
│   ├── src-gen/                        # Xtext generated infrastructure
│   └── xtend-gen/                      # Compiled Xtend code
│
├── iiit.ac.in.smartcitymodel.ide/      # IDE support
├── iiit.ac.in.smartcitymodel.ui/       # Eclipse UI
├── iiit.ac.in.smartcitymodel.tests/    # Tests
│
├── example.smartcitymodel              # Sample DSL model
├── requirements.txt                    # Python dependencies
└── *.md                                # Documentation
```

---

## 🎯 What This DSL Does

### Input: DSL Model (`.smartcitymodel`)
```
network bacnet_network {
    virtualClientIP "192.168.1.100"
    port 47808
}

node daikin_hvac_unit1 {
    ip "192.168.1.50"
    vendor "Daikin"
    
    object temperature_sensor {
        type ANALOG_INPUT
        instance 1
        priority 10
    }
    
    controller hvac_controller {
        type HVAC_CONTROLLER
        priority 1
    }
    
    postFrequency 1 SECONDS
    priority 5
}

simulationProperties {
    terminationTime 300
    syncInterval 1
}
```

### Output: PyDEVS Simulation Code
```
src-gen/
├── bacnet_objects/
│   └── daikin_hvac_unit1_temperature_sensor.py  # Atomic DEVS model
├── nodes/
│   └── daikin_hvac_unit1.py                     # Aggregator
├── layers/
│   └── bacnet_interface.py                      # BACnet communication
├── config/
│   └── bacnet_config.json                       # Configuration
├── model.py                                     # Coupled DEVS model
└── experiment.py                                # Simulation runner
```

---

## 🛠️ Technology Stack

- **Xtext 2.39.0** - DSL framework
- **Xtend** - Template-based code generation
- **Eclipse** - IDE and tooling
- **PyDEVS** - DEVS simulation engine
- **BACnet** - Building automation protocol
- **Python 3.x** - Target runtime

---

## 📦 Generated Code Features

Each generated component includes:

### ✅ Atomic DEVS Models (BACnet Objects)
- State management (value, timestamp, status)
- Input/output ports
- Transition functions (internal/external)
- Time advance logic
- BACnet property mapping

### ✅ Node Aggregators
- Multi-object data collection
- Configurable post frequency
- Controller integration
- Network interface

### ✅ Coupled Model
- Component wiring
- Port connections
- Hierarchical structure
- Simulation orchestration

### ✅ BACnet Interface
- Network configuration
- Real-time synchronization
- Multi-device support
- Communication buffering

### ✅ Configuration Files
- JSON-formatted
- Complete parameter export
- Runtime reconfigurable

---

## 🎓 Use Cases

1. **Digital Twin Development**
   - Model physical HVAC systems
   - Simulate before deployment
   - Test control strategies

2. **HVAC System Design**
   - Rapid prototyping
   - Performance analysis
   - Component sizing

3. **BACnet Protocol Testing**
   - Network simulation
   - Load testing
   - Communication verification

4. **Building Automation**
   - Multi-zone HVAC
   - Energy optimization
   - Fault detection

---

## 🔧 Customization

### Add New BACnet Object Types
Edit `SmartCityModelGenerator.xtend`:
```xtend
def String generateBacnetObjectAtomic(Node node, BacnetObject obj) '''
    // Add switch case for new type
    switch obj.type {
        case ANALOG_VALUE: '''...'''
        case MULTI_STATE_INPUT: '''...'''
    }
'''
```

### Modify DEVS Timing
```xtend
def generateBacnetObjectAtomic(...) '''
    def timeAdvance(self):
        # Customize timing logic here
        return self.sigma
'''
```

### Extend Node Aggregation
```xtend
def generateNodeAtomic(...) '''
    def extTransition(self, inputs):
        # Add filtering, averaging, etc.
'''
```

---

## 📊 Example Workflow

```
1. Model HVAC System
   ↓
   Write DSL (example.smartcitymodel)
   
2. Generate Code
   ↓
   Save file in Eclipse → Auto-generates Python
   
3. Review Output
   ↓
   Check src-gen/ for PyDEVS models
   
4. Run Simulation
   ↓
   python src-gen/experiment.py
   
5. Analyze Results
   ↓
   Modify DSL and regenerate
```

---

## 🐍 Running Generated Code

### Install Dependencies
```bash
pip install -r requirements.txt
```

### Run Simulation
```bash
cd src-gen
python experiment.py
```

### Expected Output
```
Starting BACnet HVAC simulation for 300 seconds...
[PyDEVS simulation output...]
Simulation completed.
```

---

## 🆘 Troubleshooting

| Issue | Solution | Reference |
|-------|----------|-----------|
| MWE2 fails | Check grammar syntax, clean project | [SETUP_GUIDE.md](SETUP_GUIDE.md#step-1-generate-xtext-infrastructure) |
| No code generation | Verify file extension, check Error Log | [ECLIPSE_GUIDE.md](ECLIPSE_GUIDE.md#6-error-log-view) |
| Import errors in Xtend | Run MWE2 again, refresh workspace | [QUICK_REFERENCE.md](QUICK_REFERENCE.md#-checklist) |
| Empty src-gen/ | Check output path in plugin.xml | [SETUP_GUIDE.md](SETUP_GUIDE.md#troubleshooting) |

---

## 📞 Getting Help

After following setup steps, report back with:

1. ✅ Console output from MWE2 workflow
2. ✅ Error messages from Error Log view
3. ✅ Contents of one generated .py file (if successful)
4. ✅ Screenshot of Problems view
5. ✅ Any compilation errors

See [SETUP_GUIDE.md](SETUP_GUIDE.md#what-to-report-back) for details.

---

## 🎯 Next Steps

### Immediate Actions
- [ ] Run MWE2 workflow
- [ ] Verify Xtend compilation
- [ ] Test code generation with example
- [ ] Report results

### Future Enhancements
- [ ] Add more BACnet object types (MV, MSI, MSO, etc.)
- [ ] Implement real BACnet communication
- [ ] Add data visualization
- [ ] Create unit tests
- [ ] Add schedule support
- [ ] Implement trend logging

---

## 📄 Files Created/Modified

### Modified
- `SmartCityModelGenerator.xtend` - Complete code generator
- `.gitignore` - Added Python/Xtext ignores

### Created
- `example.smartcitymodel` - Sample DSL model
- `SmartCityModelGeneratorLauncher.java` - CLI launcher
- `requirements.txt` - Python dependencies
- `*.md` - Documentation files

See [FILE_SUMMARY.md](FILE_SUMMARY.md) for complete list.

---

## 📖 Learning Resources

- **Xtext Documentation**: https://www.eclipse.org/Xtext/documentation/
- **Xtend Guide**: https://www.eclipse.org/xtend/documentation/
- **PyDEVS**: https://msdl.uantwerpen.be/documentation/PythonPDEVS/
- **BACnet**: http://www.bacnet.org/

---

## 📜 License

[Your license here]

---

## 👨‍💻 Author

Likhith Kanigolla - IIIT Hyderabad Research Project

---

**Ready to start? Open [QUICK_REFERENCE.md](QUICK_REFERENCE.md)!** 🚀

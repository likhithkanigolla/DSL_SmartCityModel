# BACnet HVAC DSL → PyDEVS Simulation

A **Domain-Specific Language (DSL)** for modeling **BACnet-based HVAC systems** and automatically generating **PyDEVS simulation code**, enabling **Digital Twin development** for building automation and smart city research.

---

## 🚀 Purpose

This project allows you to:

* Describe HVAC systems using a custom DSL
* Automatically generate **PyDEVS-based simulation models**
* Build **digital twins** of BACnet HVAC systems
* Validate control logic before deploying to physical devices
* Extend simulations toward real BACnet integration

---

## 🏗️ System Architecture

```
.smartcitymodel (DSL)
        ↓
Xtext Parser
        ↓
EMF Model (in memory)
        ↓
Xtend Code Generator
        ↓
Python PyDEVS Code
        ↓
Simulation / Digital Twin Execution
```

### Runtime Model Flow

```
BACnet Object (Atomic DEVS)
        ↓
Node Aggregator
        ↓
Coupled DEVS Model
        ↓
BACnet Interface Layer
        ↓
Physical / Virtual BACnet Network
```

---

## 📁 Project Structure

```
DSL_BACnet/
├── iiit.ac.in.smartcitymodel/          # Main Xtext project
│   ├── src/
│   │   └── iiit/ac/in/smartcitymodel/
│   │       ├── SmartCityModel.xtext            # DSL grammar
│   │       ├── GenerateSmartCityModel.mwe2     # Xtext workflow
│   │       ├── SmartCityModelStandaloneSetup.java
│   │       ├── SmartCityModelGeneratorLauncher.java
│   │       └── generator/
│   │           └── SmartCityModelGenerator.xtend   ⭐ Core generator
│   ├── src-gen/                        # Xtext generated infrastructure
│   └── xtend-gen/                      # Compiled Xtend → Java
│
├── iiit.ac.in.smartcitymodel.ide/
├── iiit.ac.in.smartcitymodel.ui/
├── iiit.ac.in.smartcitymodel.tests/
│
├── example.smartcitymodel              # Sample DSL model
├── requirements.txt                    # Python dependencies
└── README.md                           # This document
```

---

## ✍️ DSL Example (`example.smartcitymodel`)

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

    object setpoint_temp {
        type ANALOG_OUTPUT
        instance 2
        priority 8
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

---

## 🧪 Generated Output (PyDEVS)

Saving the DSL file automatically generates:

```
src-gen/
├── bacnet_objects/
│   ├── daikin_hvac_unit1_temperature_sensor.py
│   ├── daikin_hvac_unit1_setpoint_temp.py
│   └── ...
├── nodes/
│   └── daikin_hvac_unit1.py
├── layers/
│   └── bacnet_interface.py
├── config/
│   └── bacnet_config.json
├── model.py
└── experiment.py
```

### Each Atomic DEVS Model Contains

* State variables
* Input / Output ports
* `intTransition`
* `extTransition`
* `outputFnc`
* `timeAdvance`

---

## ⚙️ Eclipse Setup (One-Time)

### 1️⃣ Run Xtext Workflow

```
Right-click GenerateSmartCityModel.mwe2
→ Run As → MWE2 Workflow
→ Wait for "BUILD SUCCESSFUL"
```

### 2️⃣ Refresh Workspace

```
Select all projects → F5
```

### 3️⃣ Verify Xtend Compilation

```
xtend-gen/.../SmartCityModelGenerator.java should exist
```

If not:

```
Right-click SmartCityModelGenerator.xtend
→ Xtend → Compile
```

---

## 🧪 Test Code Generation

```
Open example.smartcitymodel
→ Save (Ctrl/Cmd + S)
→ Check src-gen/ folder
```

Success indicators:

* No errors in Problems view
* Python files appear in `src-gen/`
* Generated files import `pypdevs.DEVS`

---

## 🐍 Running the Simulation

### Install Dependencies

```bash
pip install -r requirements.txt
```

### Run

```bash
cd src-gen
python experiment.py
```

Expected output:

```
Starting BACnet HVAC simulation...
Simulation completed.
```

---

## 🛠️ Customization Points

### Add New BACnet Object Types

Edit:

```
SmartCityModelGenerator.xtend
→ generateBacnetObjectAtomic()
```

### Modify DEVS Timing

Edit generated templates:

```
timeAdvance()
```

### Extend Node Aggregation

Edit:

```
generateNodeAtomic()
```

---

## 🆘 Troubleshooting

| Issue         | Fix                               |
| ------------- | --------------------------------- |
| MWE2 fails    | Clean project, check grammar      |
| No generation | Check `.smartcitymodel` extension |
| Empty src-gen | Check Error Log view              |
| Xtend errors  | Re-run MWE2, refresh workspace    |